package net.watermelon.user.manager;

import java.io.Serializable;
import java.util.List;

import net.watermelon.core.BaseDAO;


import net.watermelon.user.dao.UserDao;
import net.watermelon.user.vo.Menu;
import net.watermelon.user.vo.MenuVo;
import net.watermelon.user.vo.RoleMenu;
import net.watermelon.user.vo.SystemRole;
import net.watermelon.user.vo.SystemUser;
import net.watermelon.user.vo.UserMenu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MenuManager {

	public static final int ROOTMENUID = -1;
	public static final int DISABLE = 0;
	public static final int ENABLE = 1;

	@Autowired
	private BaseDAO baseDAO;
	
	@Autowired
	private UserDao userDao;

	/**
	 * 管理员获得菜单项目，提供维护的内容
	 * 
	 * @return
	 */
	public List adminGetRootCommand() {
		return commandList(ROOTMENUID);
	}

	/**
	 * 增加根菜单
	 * 
	 * @param menu
	 * @return
	 */
	public Menu addRootMenu(Menu menu) {
		menu.setParentId(ROOTMENUID);
		return saveMenu(menu);

	}

	/**
	 * 增加下级菜单
	 * 
	 * @param menu
	 * @param parentId
	 * @return
	 */
	public Menu addSonMenu(Menu menu, int parentId) {
		menu.setParentId(parentId);
		return saveMenu(menu);

	}

	public Menu getMenu(int i) {
		Menu menu = (Menu) baseDAO.getOneObject(Menu.class, (Serializable) i);
		return menu;
	}

	public Menu modifyMenu(Menu menu) {
		return saveMenu(menu);
	}

	public Menu disable(Menu menu) {
		menu.setAvailable(DISABLE);
		return saveMenu(menu);
	}

	public Menu enable(Menu menu) {
		menu.setAvailable(ENABLE);
		return saveMenu(menu);

	}

	
	//这里的菜单返回的是用户菜单
	public List<Menu> getUserRootMenu(String userid) {
		String param[] = { "userId" };
		Object value[] = { userid };
		List<Menu> list = baseDAO.getObjectLists(
				"select T1  from UserMenuCo T1 where "
						+ " T1.available=" + ENABLE + " and  T1.userId=:userId and T1.parentId=-1  ORDER BY  pos",
				param, value);
		return list;
	}

	/**
	 * 管理员获得菜单列表,Manger的编程原则是 一个 测试用例对应一个函数吧，这样
	 * 
	 * @return
	 */
	private List<Menu> commandList(int p) {
		String param[] = { "parentId" };
		Object value[] = { p };
		List<Menu> list = baseDAO.getObjectLists(
				"from Menu where parentId=:parentId order by pos", param, value);
		return list;
	}

	private Menu saveMenu(Menu menu) {
		if (menu.getId() != 0)
			baseDAO.saveObject(menu);
		else
			baseDAO.add(menu);
		return menu;
	}

	/**
	 * 给某个用户增加菜单
	 * 
	 * @param menuid
	 * @param userid
	 */
	@Transactional
	public void assignMenuToUser(int menuid, String userid) {
		// 判断原来有没有
		boolean f = hasMenu(menuid, userid);
		if (f) {
			return;
		} 
		   addUserMenu(menuid,userid);
	}
	
	
	@Transactional
	public void unAssignMenuToUser(int menuid, String userid) {
		// 判断原来有没有
		boolean f = hasMenu(menuid, userid);
		if (f) {
			deleteUserMenu(menuid,userid);
		} 
		return ;
	}
	
	

	private boolean hasMenu(int menuid, String userid) {
		String param[] = { "userid", "menuid" };
		Object value[] = { userid, menuid };
		List<Menu> list = baseDAO.getObjectLists(
				"from UserMenu where userId= :userid and menuId =:menuid",
				param, value);
		if (list.size() > 0)
			return true;
		else
			return false;
	}
	
	
	private int deleteUserMenu(int menuid, String userid) {
		String param[] = { "userid", "menuid" };
		Object value[] = { userid, menuid };
		int  count = baseDAO.updateSQLStr(
				"delete from UserMenu where userId= :userid and menuId =:menuid",
				param, value);
		return count;
	}
	
	
	private void addUserMenu(int menuid, String userid) {
		UserMenu userMenu = new UserMenu(menuid,userid);
		baseDAO.saveObject(userMenu);
		//寻找menu的上级菜单，保存
		Menu menu = getMenu(menuid);
		if (menu.getParentId()!= ROOTMENUID)
			assignMenuToUser(menu.getParentId(),userid);
	}

	@Transactional
	public void dndMenu(MenuVo menuVo) {
		Integer menuId = Integer.parseInt(menuVo.getId());
		Integer menuParentId = Integer.parseInt(menuVo.getParent());
		Integer pos= Integer.parseInt(menuVo.getPosition());
		Integer oldParentId = Integer.parseInt(menuVo.getOldParent());
		Menu menu = (Menu) baseDAO.getOneObject(Menu.class, menuId);
		Integer oldPos = menu.getPos();
		menu.setParentId(menuParentId);
		if(oldParentId==menuParentId){
			if(pos>oldPos){
				baseDAO.updateSQLStr("update Menu set pos = pos -1 where pos<="+
						 (pos) + "and pos>"+oldPos+"   and parentId =" + menuParentId);

			}
		if(oldPos>pos){
			baseDAO.updateSQLStr("update Menu set pos = pos + 1 where pos<"+
					 (oldPos) + "and pos>="+pos +"  and parentId =" + menuParentId);
		 }
		}
		else{
			baseDAO.updateSQLStr("update Menu set pos = pos +1 where pos>="+
					 (pos) + "and parentId =" + menuParentId);
			baseDAO.updateSQLStr("update Menu set pos = pos -1 where pos>"+
							oldPos + "and parentId =" + oldParentId);
			
		}
		menu.setPos(pos);
		baseDAO.saveObject(menu);
		
	}

	public List<Menu> getUserSonMenu(String userId, String menuid) {
		String param[] = { "userId" ,"menuid"};
		Object value[] = { userId,	Integer.parseInt(menuid) };
		List<Menu> list = baseDAO.getObjectLists(
				"select T1  from UserMenuCo T1 where "
						+ " T1.available=" + ENABLE + " and  T1.userId=:userId  and T1.parentId=:menuid  order by pos", 
				 param, value);
		
		return list;
	}

	
	/**
	 * 获得商品目录分类
	 * @return
	 */
	public List<Menu> getCategory() { 
		String param[] = { "parentId","style" };
		Object value[] = { -1,"cat" };
		List<Menu> list = baseDAO.getObjectLists(
				"from Menu where parentId=:parentId and style=:style order by pos", param, value);
		return list;
	}

	/**
	 * 保存角色对应菜单
	 * @param roleid
	 * @param treeid
	 */
	public void saveRoleMenu(String roleid, String[] treeids) {
		//删除RoleID 的Menu 设置
		baseDAO.updateSQLStr("delete from RoleMenu where roleId="+roleid);
		//保存每个 Role TreeId
		if(treeids == null ) return;
		for(String treeid :treeids){
			Integer tree = Integer.parseInt(treeid);
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setMenuId(tree);
			roleMenu.setRoleId( Integer.parseInt(roleid));
			baseDAO.saveObject(roleMenu);
		}
		
		
	}

	
	/**
	 * 获得用户菜单
	 */
	public List getUserRoleMenu(Integer userId){
		//先获得用户
		SystemUser systemUser =(SystemUser) userDao.getOne(userId);
		List<SystemRole> roles = systemUser.getRoles();
		for(SystemRole role:roles){
		List menuList =	baseDAO.getObjectLists("from  RoleMenu where roleId="+role.getId());
		}
		return null;
	}
	
}

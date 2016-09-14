package net.watermelon.user.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.watermelon.core.CommonManager;
import net.watermelon.core.DataTableParam;
import net.watermelon.core.DataTableVo;
import net.watermelon.user.dao.RoleDao;
import net.watermelon.user.manager.MenuManager;
import net.watermelon.user.vo.Menu;
import net.watermelon.user.vo.RoleSearch;
import net.watermelon.user.vo.SystemRole;

import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import net.watermelon.user.vo.*;
@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private  RoleDao<SystemRole> roleDao;

	
	@Autowired
	private MenuManager menuManager;
	/**
	 * 初始化到列表页面
	 * @return
	 */

	@RequestMapping("/index")
	public String indexSearch(Model model) {
		return "/role/index";
	}

	@ModelAttribute("pageName")
	private String pageName(){
		return "RolePage";
	}
	
	@ModelAttribute("smallName")
	private String smallName(){
		return "RolePageList";
	}
	
	@ModelAttribute("panelTitle")
	private String panelTitle(){
		return "角色管理列表";
	}
	
	@Autowired
	CommonManager commonManager;
	
	/**
	 * 返回列表的JSON
	 * 
	 * @param dataTableParam
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/role-json")
	public DataTableVo<?> dataTableDemo(@ModelAttribute RoleSearch roleSearch,HttpServletRequest request,
			HttpServletResponse response, Model model) {
		//定义表格的显示列
		DataTableParam dataTableParam = new DataTableParam(request);
			String sql = " from SystemRole ";
			
		if(null!=roleSearch) 	sql = sql +roleSearch.getSQL();
		
		if (StringUtil.isNotBlank(dataTableParam.getSearchValue()))
			sql = sql + " and name  like '" + dataTableParam.getSearchValue()
					+ "%'";
		if (StringUtil.isNotBlank(dataTableParam.getOrderby()))
			sql = sql + "order by " + dataTableParam.getOrderby() + " "
					+ dataTableParam.getOrderdir();
		DataTableVo<SystemRole> datatable = commonManager.getList(sql,
				dataTableParam);
		return datatable;
	}

	
/**
 * 增加
 * @param model
 * @return
 */
	
	@RequestMapping("/edit")
	 public String setupForm( Model model) {
		SystemRole  systemRoler = null;
		systemRoler = new SystemRole();
		model.addAttribute(systemRoler);
		return "/role/edit";
	}

	
	/**
	 * 初始化添加页面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit/{id}")
	 public String setupForm(@PathVariable("id")  Integer id, Model model) {
		SystemRole  systemRole = null;
		if (id == 0) {
			systemRole = new SystemRole();
	
		} else
			systemRole = roleDao.getOne(id);
		    model.addAttribute(systemRole);
		return "/role/edit";
	}
	
	
	@RequestMapping("/save")
	public String save(@Valid @ModelAttribute SystemRole systemRole,BindingResult result,SessionStatus status, Errors errors) {
		if (errors.hasErrors()) {
				return "/user/edit";
		} else {
				roleDao.save(systemRole);
				status.setComplete();
			return "redirect:/user/index";
		}

	}
	
	
	@RequestMapping("delete/{id}")
    public String delete(@PathVariable("id")  Integer id) {
		roleDao.delete(id);
		return "redirect:/role/index";
    }
	
	
	
	@RequestMapping("/menu")
	public String menu(Model model) {
		model.addAttribute("panelTitle","角色菜单管理");
	
		model.addAttribute("roles",roleDao.findAll());
		return "/role/roleMenu";
	}
	
	
	@ResponseBody
	@RequestMapping("/menu/category-json")
	public List getCategory(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		List menu = menuManager.getCategory();
		return menu;
	}
	
	/**
	 * 点击角色，获得角色菜单，和主菜单比较打勾，返回带状态的MenuList
	 */
	@ResponseBody
	@RequestMapping("/menu/category-role-json")
	public List getRoleCategory(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		
		String roleid = request.getParameter("roleid");
		System.out.println(roleid);
		if(roleid==null) roleid="1";
		List<Menu> menus = menuManager.getCategory();
		List<RoleMenu> roleMenus = commonManager.getObjectLists(" from RoleMenu where roleId="+roleid);
		
		for(RoleMenu roleMenu:roleMenus){
      			setMenuCheck(menus,roleMenu.getMenuId());
    		}
 		return menus;
	}
	
	
	@ResponseBody
	@RequestMapping("/menu/save-role-category-json")
	public String saveRoleCategory(Model model, HttpServletRequest request,
			HttpServletResponse response) {
	String[] treeid = 	request.getParameterValues("treeid[]"); //这样获得传递的数组
	String roleid = 	request.getParameter("roleid");
//保存到数据库中
	menuManager.saveRoleMenu(roleid,treeid);
	return "success";
	}
	
	private void setMenuCheck(List<Menu> menus, Integer  menuid){
		for(Menu menu:menus){

			if (menu.getId() == (menuid)){
				menu.getState().setSelected(true);
				menu.getState().setChecked(true);
				menu.getState().setOpened(true);
			}
			
		List 	sonMenus = menu.getChildren();
			if(sonMenus!=null && sonMenus.size()>0){
				setMenuCheck(sonMenus,  menuid);
			}
	}
	}
	
}

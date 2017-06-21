package net.watermelon.org.manager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.watermelon.org.dao.OrganizationDao;
import net.watermelon.org.vo.Organization;
import net.watermelon.tree.Tree;
import net.watermelon.tree.dao.TreeDao;
import net.watermelon.user.dao.UserDao;
import net.watermelon.user.vo.SystemUser;
@Service
public class OrganzationManager {
	
	@Autowired
	private OrganizationDao<Organization> organizationDao;
	@Autowired
	private TreeDao<Tree> treeDao;
	
	@Autowired
	private UserDao <SystemUser> userDao;
	
	
	@Transactional
	public void save(Organization org){
		
		if(org.getId() ==null){
			Tree tree  = new Tree();
			tree.setText(org.getName());
			tree.setParentId(org.getTreeId());
            treeDao.save(tree);
            org.setTreeId(tree.getId());
            organizationDao.save(org);
            
		}
		else{
		Tree tree =  treeDao.getOne(org.getTreeId());
		tree.setText(org.getName());
		treeDao.save(tree);
		organizationDao.save(org);
		}
	}
	
	 /**
	  * 获得下设机构
	  * @param treeId
	  * @return
	  */
	public List<Organization> getSonOrgs(Integer  treeId){
		Tree tree =  treeDao.getOne(treeId);
		List<Tree> children = tree.getChildren();
		List <Organization> orgs = new ArrayList<Organization>();
		for(Tree t:children){
			Organization org = organizationDao.findByTreeId(t.getId());
			org.setPos(t.getPos());
			orgs.add(org);
		}
		return orgs;
	}
	
	/**
	 * 获得下设人员
	 */
	
	public List<SystemUser> getOrgMembers(Integer  treeId){
		Tree tree =  treeDao.getOne(treeId);
	Organization organization = 	organizationDao.findByTreeId(treeId);
	List<SystemUser>  list= 	userDao.findByGroupId(organization.getId());
	return list;
	}
	
	
	public List<SystemUser> getUnOrgMembers(){
		List<SystemUser>  list= 	userDao.findByGroupId(null);
		return list;
	}
	
	
	public List<Organization> getUnOrg(){
		List<Organization>  list= 	organizationDao.getUnTreeOrg();
		return list;
	}
	@Transactional
	public void savePos(Integer[] treeId, Integer[] pos) {
		// TODO Auto-generated method stub
		for(int i=0;i<treeId.length;i++){
			Tree tree = treeDao.getOne(treeId[i]);
			tree.setPos(pos[i]);
			treeDao.save(tree);
		}
	}
	@Transactional
	public void saveUnOrg(Integer[] idx,Integer treeId) {
		// TODO Auto-generated method stub
		for(int i=0;i<idx.length;i++){
			Organization organization  = organizationDao.getOne(idx[i]);
			Tree tree = new Tree();
			tree.setText(organization.getName());
			tree.setParentId(treeId);
			treeDao.save(tree);
			organization.setTreeId(tree.getId());
			organizationDao.save(organization);
		}
	}

	
	/**
	 * 取消组织机构的树关系
	 * @param treeId
	 * @param orgId
	 * @throws Exception 
	 */
	@Transactional
	public void removeTreeRela(Integer  orgId) throws Exception {
	
		Organization organization  = organizationDao.getOne(orgId);
		Tree tree = treeDao.getOne(organization.getTreeId());
		if(tree.getChildren().size()>0){
			throw new Exception("只能删除叶子节点");
		}
		treeDao.delete(organization.getTreeId());
		organization.setTreeId(null);
		organizationDao.save(organization);
	
	}
}

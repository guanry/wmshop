package net.watermelon.org.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.watermelon.admin.menu.vo.MenuMenu;
import net.watermelon.core.CommonManager;
import net.watermelon.core.DataTableParam;
import net.watermelon.core.DataTableVo;
import net.watermelon.core.ValidationMessage;
import net.watermelon.core.ValidationResponse;
import net.watermelon.org.dao.OrganizationDao;
import net.watermelon.org.manager.OrganzationManager;
import net.watermelon.org.vo.Organization;
import net.watermelon.org.vo.OrganizationSearch;
import net.watermelon.resource.dao.ResourceDao;
import net.watermelon.resource.vo.Resource;
import net.watermelon.resource.vo.ResourceSearch;
import net.watermelon.tree.Tree;
import net.watermelon.user.vo.Menu;
import net.watermelon.user.vo.RoleMenu;
import net.watermelon.user.vo.SystemUser;

import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/org")
public class OrganizationController {

	/**
	 * 初始化到列表页面
	 * 
	 * @return
	 */

	@RequestMapping("/index")
	public String indexSearch(Model model, HttpSession session) {
		session.removeAttribute("orgPath");
		return "/org/index";
	}

	@ModelAttribute("pageName")
	private String pageName() {
		return "OrganizationPage";
	}

	@ModelAttribute("smallName")
	private String smallName() {
		return "OrganizationPageList";
	}

	@ModelAttribute("panelTitle")
	private String panelTitle() {
		return "组织机构管理列表";
	}

	@Autowired
	CommonManager commonManager;

	@Autowired
	OrganizationDao<Organization> organizationDao;
	
	@Autowired
	OrganzationManager organzationManager;

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
	@RequestMapping("/org-json")
	public DataTableVo<?> dataTableDemo(
			@ModelAttribute OrganizationSearch organizationSearch,
			HttpServletRequest request, HttpServletResponse response,
			Model model, HttpSession session) {
		// 定义表格的显示列
		Integer orgPath = (Integer) session.getAttribute("orgPath");
		if (orgPath != null)
			organizationSearch.setUpId(orgPath.toString());

		DataTableParam dataTableParam = new DataTableParam(request);
		String sql = "from Organization  ";

		if (null != organizationSearch)
			sql = sql + organizationSearch.getSQL();

		if (StringUtil.isNotBlank(dataTableParam.getSearchValue()))
			sql = sql + " and name  like '" + dataTableParam.getSearchValue()
					+ "%'";
		if (StringUtil.isNotBlank(dataTableParam.getOrderby()))
			sql = sql + "order by " + dataTableParam.getOrderby() + " "
					+ dataTableParam.getOrderdir();
		DataTableVo<Resource> datatable = commonManager.getList(sql,
				dataTableParam);
		return datatable;
	}

	/**
	 * 增加
	 * 
	 * @param model
	 * @return
	 */

	@RequestMapping("/edit")
	public String setupForm(Model model) {
		Organization org = null;
		org = new Organization();
		model.addAttribute(org);
		return "/org/edit";
	}

	/**
	 * 初始化添加页面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit/{id}")
	public String setupForm(@PathVariable("id") Integer id, Model model) {
		Organization org = null;
		if (id == 0) {
			org = new Organization();
		} else
			org = (Organization) organizationDao.getOne(id);
		model.addAttribute(org);
		return "/org/edit";
	}

	@RequestMapping("/save")
	public String saveMenu(@Valid @ModelAttribute Organization org,
			BindingResult result, SessionStatus status, Errors errors) {
		if (errors.hasErrors()) {
			return "/org/edit";
		} else {
			organizationDao.save(org);
			status.setComplete();
			return "redirect:/org/index";
		}

	}

	@RequestMapping("delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		organizationDao.delete(id);
		return "redirect:/org/index";
	}

	/**
	 * 获得下级部门列表页面
	 */
	@RequestMapping("index/{id}")
	public String getSonList(@PathVariable("id") Integer id, HttpSession session) {
		session.setAttribute("orgPath", id);
		return "/org/sonindex";
	}

	/**
	 * 获得部门人员列表
	 */
	@RequestMapping("user")
	public String getUserList() {
		return "/org/userindex";
	}
	
	
	/**
	 * 部门管理主界面
	 */
	@RequestMapping("main")
	public String main(){
		return "/org/main";
		
	}
	
	private  static final int ROOTMENUID = -1;
	/**
	 * 部门Tree数据
	 */
	@ResponseBody
	@RequestMapping("org-tree-json")
	public List<Tree> getTree() {
		List<Tree> trees = commonManager.getObjectLists(" from Tree where catalog ='ORG' and parent="+ ROOTMENUID);
    	return trees;
	}
	
	@ResponseBody
	@RequestMapping("/org-form/{id}")
	public Organization getOrgJson(@PathVariable("id") Integer id,Model model) {
		Organization organization = null;
		organization = (Organization) organizationDao.findByTreeId(id);
		return organization;
	}
		
	
	/**
	 * Json 数据保存，不成功返回错误
	 * @param organization
	 * @param result
	 * @param status
	 * @param errors
	 * @return
	 */
	//{"status":"FAIL","result":[{"fieldName":"firstName","message":"firstName  may not be empty"}]}
	@ResponseBody
	@RequestMapping("/org_save_json")
	public ValidationResponse orgSaveJson(@Valid @ModelAttribute Organization organization,
			BindingResult result, SessionStatus status, Errors errors) {
		ValidationResponse validationResponse = new ValidationResponse();
		if (errors.hasErrors()) {
			validationResponse.setStatus("FAIL");
			List<ValidationMessage>  list = new ArrayList<ValidationMessage>();
			for (FieldError error : errors.getFieldErrors()) {
				ValidationMessage  message = new ValidationMessage(); 
				message.setFieldName(error.getField());
				message.setMessage(error.getDefaultMessage());
				list.add(message);
			}
			validationResponse.setErrorMessageList(list);
		} else {
			 organzationManager.save(organization);
		 	 status.setComplete();
			 validationResponse.setStatus("SUCCESS");
		}
		return validationResponse;
	}
	
	
	/**
	 * 下设部门列表
	 */
	@RequestMapping("/org_son_json/{id}")
	public String getSonOrg(@PathVariable("id") Integer id,Model model){
		List<Organization>  orgs = organzationManager.getSonOrgs(id);
		model.addAttribute("orgs",orgs);
		return "/org/orgSons::orgSons";
	}
	/**
	 * 下设人员
	 */
	@RequestMapping("/org_member_json/{id}")
	public String getOrgPerson(@PathVariable("id") Integer id,Model model){
		List<SystemUser>  members = organzationManager.getOrgMembers(id);
		model.addAttribute("members",members);
		return "/org/orgSons::orgMembers";
	}
	/**
	 * 未管理部门
	 */
	@RequestMapping("/org_un_member_json")
	public String getUnManagerPerson(Model model){
		List<SystemUser>  members = organzationManager.getUnOrgMembers();
		model.addAttribute("members",members);
		return "/org/orgSons::orgMembers";
		
	}
	
	/**
	 * 未管理人员
	 */
	@RequestMapping("/org_un_menager_json")
	public String getUnManageOrg(Model model){
		List<Organization>  orgs = organzationManager.getUnOrg();
		model.addAttribute("orgs",orgs);
		return "/org/orgSons::orgUnManager";
		
	}
	
	/**
	 * 未管理部门记入此部门
	 */
	@ResponseBody
	@RequestMapping("/add_org_list")
	public String addUnOrg(Integer[] idx){
		organzationManager.saveUnOrg(idx);
			return "/org/orgSons::orgSons";
	}
	
	/**
	 * 保存部门排列位置
	 * @param treeId
	 * @param pos
	 * @param status
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/org_save_pos_json")
	public ValidationResponse org_save_pos_json(Integer[] treeId,Integer [] pos, SessionStatus status){
		organzationManager.savePos(treeId,pos);
		ValidationResponse validationResponse = new ValidationResponse();
		 status.setComplete();
		 validationResponse.setStatus("SUCCESS");
		return validationResponse;
	}
}

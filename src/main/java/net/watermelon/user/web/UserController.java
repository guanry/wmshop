package net.watermelon.user.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.watermelon.core.CommonManager;
import net.watermelon.core.DataTableParam;
import net.watermelon.core.DataTableVo;
import net.watermelon.resource.vo.Resource;
import net.watermelon.user.dao.RoleDao;
import net.watermelon.user.dao.UserDao;
import net.watermelon.user.vo.SystemRole;
import net.watermelon.user.vo.SystemUser;
import net.watermelon.user.vo.UserSearch;






import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserDao<SystemUser> userDao;

	/**
	 * 初始化到列表页面
	 * @return
	 */

	@RequestMapping("/")
	public String indexSearch(Model model) {
		return "/user/index";
	}

	@ModelAttribute("pageName")
	private String pageName(){
		return "UserPage";
	}
	
	@ModelAttribute("smallName")
	private String smallName(){
		return "UserPageList";
	}
	
	@ModelAttribute("panelTitle")
	private String panelTitle(){
		return "人员管理列表";
	}
	
	@Autowired
	CommonManager commonManager;
	
	@Autowired
	MessageDigestPasswordEncoder passwordEncoder;
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
	@RequestMapping("/user-json")
	public DataTableVo<?> dataTableDemo(@ModelAttribute UserSearch userSearch,HttpServletRequest request,HttpSession session) {
		//定义表格的显示列
		
		Integer orgPath = (Integer) session.getAttribute("orgPath");
		if (orgPath != null)
			userSearch.setGroupId(orgPath.toString());
		
		DataTableParam dataTableParam = new DataTableParam(request);
			String sql = " from SystemUser";
			
		sql = sql +userSearch.getSQL();
		
		if (!StringUtils.isEmpty(dataTableParam.getSearchValue()))
			sql = sql + " and login  like '" + dataTableParam.getSearchValue()
					+ "%'";
		if (!StringUtils.isEmpty(dataTableParam.getOrderby()))
			sql = sql + "order by " + dataTableParam.getOrderby() + " "
					+ dataTableParam.getOrderdir();
		DataTableVo<SystemUser> datatable = commonManager.getList(sql,
				dataTableParam);
		return datatable;
	}

	
/**
 * 增加
 * @param model
 * @return
 */
	@Autowired
	private  RoleDao<SystemRole> roleDao;
	
	@RequestMapping("/edit")
	 public String setupForm( Model model) {
		SystemUser  user = null;
		user = new SystemUser();
			model.addAttribute(user);
		return "/user/edit";
	}

	
	/**
	 * 初始化添加页面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{id}" , method = RequestMethod.GET )
	 public String setupForm(@PathVariable("id")  Integer id, Model model) {
		SystemUser  user = null;
		if (id == 0) {
			user = new SystemUser();
			user.setRegistered(new Date());
		} else
	    user = userDao.getOne(id);
		List<SystemRole> allRoles = roleDao.findAll();
		model.addAttribute("allRoles",allRoles );   
		model.addAttribute(user);
		return "/user/edit";
	}
	
	
	/**
	 * 保存更新
	 * @param user
	 * @param result
	 * @param status
	 * @param errors
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String save(@Valid @ModelAttribute SystemUser user,BindingResult result,SessionStatus status, Errors errors) {
		if (errors.hasErrors()) {
				return "/user/edit";
		} else {
	 	//设置缺省密码 123456
		String pp = passwordEncoder.encodePassword("123456",user.getLogin());
		user.setPass(pp);
		userDao.save(user);
		status.setComplete();
	 	return "redirect:/users/";
		}

	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id")  Integer id) {
		userDao.delete(id);
		return "redirect:/users/";
    }
	
}

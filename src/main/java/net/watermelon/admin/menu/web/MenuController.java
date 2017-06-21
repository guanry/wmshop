package net.watermelon.admin.menu.web;

import java.util.List;






import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid; 






import net.watermelon.admin.menu.manager.MenuRepository;
import net.watermelon.admin.menu.vo.MenuMenu;



import net.watermelon.core.CommonManager;
import net.watermelon.core.DataTableParam;
import net.watermelon.core.DataTableVo;


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


@Controller
@RequestMapping("/menu")
public class MenuController {
	
	 
	@Autowired
	CommonManager commonManager;

/**
 * s设置页面下拉框
 * @return
 */
	
	@ModelAttribute("parentMenu")
	private List<MenuMenu> parentMenu(){
		List<MenuMenu> menuList =  (List<MenuMenu>) menuRepository.findByParentAndEnabled(-1,1);
		return menuList;
	}
	
	@ModelAttribute("pageName")
	private String pageName(){
		return "MenuPage";
	}
	
	@ModelAttribute("smallName")
	private String smallName(){
		return "MenuPageList";
	}
	
	@ModelAttribute("panelTitle")
	private String panelTitle(){
		return "菜单管理列表";
	}
	
	/** 列表 **/
	
	@RequestMapping("/index")
    public String index() {
	    return "menu/index";
    }
	

	@ResponseBody
	@RequestMapping("/menu-json")
	public DataTableVo<?> dataTableDemo(@ModelAttribute MenuSearchCriteria menuSearchCriteria,HttpServletRequest request,
			HttpServletResponse response, Model model) {
		//定义表格的显示列
		DataTableParam dataTableParam = new DataTableParam(request);
			String sql = "" ;
		//	menuSearchCriteria.setParent("-1");
			//menuSearchCriteria.setEnabled("1");
			if(null!=menuSearchCriteria) 	sql = sql + menuSearchCriteria.getSQL();
			if (StringUtil.isNotBlank(dataTableParam.getSearchValue()))
			sql = sql + " and name  like '" + dataTableParam.getSearchValue()
					+ "%'";
		if (StringUtil.isNotBlank(dataTableParam.getOrderby()))
			sql = sql + " order by " + dataTableParam.getOrderby() + " "
					+ dataTableParam.getOrderdir();
		DataTableVo<MenuMenu> datatable = commonManager.getList(sql,
				dataTableParam);
		return datatable;
	}

	
	
//	//下级菜单列表
//	@RequestMapping("/index/{id}")
//    public String index(@PathVariable("id")  Integer id,  MenuSearchCriteria menuSearch,Model model) {
//	     menuSearch.setParent(id.toString());
//        List<Menu> list =  commonManager.getObjectLists(menuSearch.getSQL());
//	    model.addAttribute("list",list);
//        return "/menu/index"; 
//    }
//	
	
	//获得Menu页面
		@RequestMapping("/edit")
		 public String setupForm( Model model) {
			MenuMenu menu = null;
			menu = new MenuMenu();
			model.addAttribute(menu);
			return "/menu/edit";
		}
	
	
	//获得Menu页面
	@RequestMapping("/edit/{id}")
	 public String setupForm(@PathVariable("id")  Integer id, Model model) {
		MenuMenu menu = null;
		if (id == 0) {
			menu = new MenuMenu();
		} else
			menu =	menuRepository.getOne(id);
		    model.addAttribute(menu);
		return "/menu/edit";
	}
	
	/**
	 * 保存菜单@Valid PersonForm personForm, BindingResult bindingResult
	 * 
	 * @param model
	 * @param request
	 * @param response
	 */
	@RequestMapping("/save")
	public String saveMenu(@Valid @ModelAttribute MenuMenu menu, BindingResult result,SessionStatus status, Errors errors) {
		if (errors.hasErrors()) {
				return "/menu/edit";
		} else {
			menu = menuRepository.save(menu);
			status.setComplete();
			return "redirect:/menu/index";
		}

	}

	
	@RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id")  Integer id) {
		MenuMenu menu = new MenuMenu();
		menu.setId(id);
	//	(Menu) baseDAO.getOneObject(Menu.class, id);
	//	if(null==menu){
			//加入一个错误消息
			
		//}
	//	else {
			
		//	baseDAO.deleteObject(menu);
		
	//	}
		return "redirect:/menu/index";
    }
	
	 @Autowired
	 MenuRepository menuRepository;	
	/**
	 * 获得菜单，和当前菜单项目，应该设定当前菜单
	 * @return
	 */
	@ModelAttribute("menuList")
	public List<MenuMenu> menuList(){
		List<MenuMenu> menuList =  (List<MenuMenu>) menuRepository.getMenuList();
        return menuList; // 页面菜单显示
   }
	
	
}

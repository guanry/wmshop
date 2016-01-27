package net.watermelon.admin.menu.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.watermelon.admin.menu.vo.Menu;
import net.watermelon.core.PageParam;
import net.watermelon.core.PagedList;
import net.watermelon.demo.dao.BaseDAO;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;


@Controller
public class MenuController {
	
	 
	@Autowired
	private BaseDAO<Menu> baseDAO;
	
	
	/** 列表 **/
	
	@RequestMapping("/menu/index")
    public String index(Model model) {
	    MenuSearch menuSearch = new MenuSearch();
        menuSearch.setParent("-1");
        model.addAttribute(menuSearch);
        List list =  baseDAO.getObjectLists(menuSearch.getSQL());
	    model.addAttribute("list",list);
        return "/menu/index"; 
    }
	

	//下级菜单列表
	@RequestMapping("/menu/index/{id}")
    public String index(@PathVariable("id")  Integer id,Model model) {
	    MenuSearch menuSearch = new MenuSearch();
        menuSearch.setParent(id.toString());
        model.addAttribute(menuSearch);
        List list =  baseDAO.getObjectLists(menuSearch.getSQL());
	    model.addAttribute("list",list);
        return "/menu/index"; 
    }
	
	
	//获得Menu页面
		@RequestMapping("/menu/edit")
		 public String setupForm( Model model) {
			Menu menu = null;
			menu = new Menu();
			model.addAttribute(menu);
			return "/menu/edit";
		}
	
	
	//获得Menu页面
	@RequestMapping("/menu/edit/{id}")
	 public String setupForm(@PathVariable("id")  Integer id, Model model) {
		Menu menu = null;
		if (id == 0) {
			menu = new Menu();
		} else
			menu = (Menu) baseDAO.getOneObject(Menu.class, id);
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
	@RequestMapping("/menu/save")
	public String saveMenu(@Valid @ModelAttribute Menu menu, BindingResult result,SessionStatus status) {
		if (result.hasErrors()) {
			return "/menu/edit";
		} else {
			baseDAO.saveObject(menu);
			status.setComplete();
			return "redirect:/menu/index";
		}

	}
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/menu/search")
    public String index(@ModelAttribute MenuSearch menuSearch,Model model) {
	//	PageParam pageParam = PageParam.getPagePara(request);
		List list =  baseDAO.getObjectLists(menuSearch.getSQL());
	    model.addAttribute("list",list);
        return "/menu/index"; 
    }
}

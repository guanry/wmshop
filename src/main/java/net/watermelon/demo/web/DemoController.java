package net.watermelon.demo.web;


import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.watermelon.admin.menu.manager.MenuRepository;
import net.watermelon.core.CommonManager;
import net.watermelon.core.DataTableParam;
import net.watermelon.core.DataTableVo;
import net.watermelon.demo.vo.Site;
import net.watermelon.demo.vo.SiteRepository;

import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class DemoController {
	
	@Autowired 
	SiteRepository<Site> siteRepository; 
	
	@Autowired 
	MenuRepository menuRepository; 
	
	@Autowired
	CommonManager commonManager;
	
	@RequestMapping("/greeting/{username}")
	    public String greeting(@PathVariable("username")  String name, Model model) {
		
			List<Site> list = (List<Site>) siteRepository.findAll();
	        model.addAttribute("name", name);
	        model.addAttribute("list", list);
	        return "/demo/hello"; 
	    }
	
	
	@RequestMapping("/blank")
	 public String blank(){
	 return "/demo/blank_page";
	}
	

	@RequestMapping("/layout/list")
	 public String ui_colors(Model model){
		 model.addAttribute("name", "guanxi");
	 return "/layout/list";
	}
	
	@RequestMapping("/edited_table")
	 public String uicolors(){
	 return "/demo/edited_table";
	}
	
	@ResponseBody
	@RequestMapping("/page-json")
	public DataTableVo<?> dataTableDemo(	HttpServletRequest request, HttpServletResponse response,
			Model model) {
		
		DataTableParam dataTableParam = new DataTableParam(request);
		//这里拼接SQL
		String sql = "from Site where 1=1 ";
		if ( StringUtil.isNotBlank(dataTableParam.getSearchValue()))	
			   sql =sql  + 	" and name  like '" 		+ dataTableParam.getSearchValue() +"%'" ;
		if ( StringUtil.isNotBlank(dataTableParam.getOrderby()))	
		  	sql = sql +  "order by "+dataTableParam.getOrderby() + " "  + dataTableParam.getOrderdir() ; 
		DataTableVo<Site> datatable = commonManager.getList(sql, dataTableParam);
    	//	DataTableVo<Site> datatable = siteRepository.getList(name, pageable)getList(sql, dataTableParam);
 		return datatable ;
	}
	/**
	 * 复杂的脚本控制放在这里,返回页面的部分内容
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */

	@RequestMapping("/table-json")
	public String table(HttpServletRequest request, HttpServletResponse response,	Model model) {
		//这里直接返回 JS
		return "fragment/js :: Hello";
	}
}

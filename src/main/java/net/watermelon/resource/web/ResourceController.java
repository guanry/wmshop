package net.watermelon.resource.web;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;





import net.watermelon.core.CommonManager;
import net.watermelon.core.DataTableParam;
import net.watermelon.core.DataTableVo;

import net.watermelon.resource.dao.ResourceDao;
import net.watermelon.resource.vo.Resource;
import net.watermelon.resource.vo.ResourceSearch;

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
@RequestMapping("/resource")
public class ResourceController {

	@Autowired
	private ResourceDao<Resource> resourceDao;

	/**
	 * 初始化到列表页面
	 * @return
	 */

	@RequestMapping("/index")
	public String indexSearch(Model model) {
		return "/resource/index";
	}

	@ModelAttribute("pageName")
	private String pageName(){
		return "ResourcePage";
	}
	
	@ModelAttribute("smallName")
	private String smallName(){
		return "ResourcePageList";
	}
	
	@ModelAttribute("panelTitle")
	private String panelTitle(){
		return "资源管理列表";
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
	@RequestMapping("/resource-json")
	public DataTableVo<?> dataTableDemo(@ModelAttribute ResourceSearch resourceSearch,HttpServletRequest request,
			HttpServletResponse response, Model model) {
		//定义表格的显示列
		DataTableParam dataTableParam = new DataTableParam(request);
			String sql = "from Resource  ";
			
		if(null!=resourceSearch) 	sql = sql + resourceSearch.getSQL();
		
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
 * @param model
 * @return
 */
	
	@RequestMapping("/edit")
	 public String setupForm( Model model) {
		Resource resource = null;
		resource = new Resource();
		model.addAttribute(resource);
		return "/resource/edit";
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
		Resource resource = null;
		if (id == 0) {
			resource = new Resource();
		} else
			resource = resourceDao.getOne(id);
		    model.addAttribute(resource);
		return "/resource/edit";
	}
	
	
	@RequestMapping("/save")
	public String saveMenu(@Valid @ModelAttribute Resource resource,BindingResult result,SessionStatus status, Errors errors) {
		if (errors.hasErrors()) {
				return "/resource/edit";
		} else {
			resourceDao.save(resource);
			status.setComplete();
			return "redirect:/resource/index";
		}

	}
	
	
	@RequestMapping("delete/{id}")
    public String delete(@PathVariable("id")  Integer id) {
		resourceDao.delete(id);
		return "redirect:/resource/index";
    }
	
	

	
}

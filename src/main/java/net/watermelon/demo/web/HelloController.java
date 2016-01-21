package net.watermelon.demo.web;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



import net.watermelon.demo.dao.BaseDAO;;

@Controller
public class HelloController {
	 
	@Autowired
	private BaseDAO baseDAO;
	
	@RequestMapping("/greeting/{username}")
	    public String greeting(@PathVariable("username")  String name, Model model) {
		
	List list = (List) baseDAO.getObjectLists("from Site");
	        model.addAttribute("name", name);
	        model.addAttribute("list", list);
	      
	        return "/demo/hello"; 
	    }
}

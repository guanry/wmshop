package net.watermelon.admin.menu.web;

import java.util.List;

import net.watermelon.admin.menu.vo.Menu;
import net.watermelon.demo.dao.BaseDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class MenuController {
	
	 
	@Autowired
	private BaseDAO<Menu> baseDAO;
	
	@RequestMapping("/menu/index.html")
    public String index(Model model) {
	 	List<Menu> list = (List<Menu>) baseDAO.getObjectLists("from Menu where parent = -1");
        model.addAttribute("list", list);
        return "/menu/index"; 
    }
	
	
}

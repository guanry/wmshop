package net.watermelon.demo.web;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
	  @RequestMapping("/greeting/{username}")
	    public String greeting(@PathVariable("username")  String name, Model model) {
	        model.addAttribute("name", name);
	        return "/demo/hello";
	    }
}

package net.watermelon.demo.web;



import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.watermelon.demo.manager.VactionProcessService;
import net.watermelon.demo.vo.Vaction;
import net.watermelon.demo.vo.VactionRepository;

import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/vaction")
public class VactionController  {
	@Autowired 
	VactionRepository<Vaction> vactionRepository; 
	
	@ModelAttribute("pageName")
	private String pageName(){
		return "创建请假单";
	}
	
	@ModelAttribute("smallName")
	private String smallName(){
		return "创建请假单";
	}
	
	@ModelAttribute("panelTitle")
	private String panelTitle(){
		return "创建请假单";
	}
	
	@RequestMapping("/edit")
	 public String setupForm( Model model) {
		Vaction  vaction = null;
		 vaction= new Vaction();
		model.addAttribute(vaction);
		return "/vaction/edit";
	}
	
	@Autowired 
	private VactionProcessService vps;
	
	@RequestMapping("/save")
	public String save(@Valid @ModelAttribute Vaction vaction ,BindingResult result,SessionStatus status, Errors errors,HttpServletRequest request,HttpServletResponse response) {
		if (errors.hasErrors()) {
				return "/vaction/edit";
		} else {
			String processId = vps.startProcess();  //开始流程
			vaction.setProcessId(processId);
			
			vactionRepository.save(vaction);  //保存到数据库
			
			status.setComplete();
			return "redirect:/vaction/index";
		}

	}
	
	/**
	 * 获得我的任务列表
	 */
	@RequestMapping("/myProcess")
	public  String myProcess(Model model,HttpServletRequest request ){
	String userName = 	"admin";//request.getUserPrincipal().getName();
	 List list =	vps.getTasks(userName);
	
	 model.addAttribute("processList",list);
		return "/vaction/myprocess";
	}
	
	/**
	 * 所有任务
	 */
	@RequestMapping("/allProcess")
	public  String allProcess(Model model,HttpServletRequest request ){
	 List<Task> list =	vps.getAllTasks();
	 model.addAttribute("processList",list);
		return "/vaction/myprocess";
	}
	/**
	 * 获得任务执行情况流程图
	 * @throws Exception 
	 */
	@RequestMapping("/graph")
	public  String getGraph(Model model,HttpServletRequest request ) throws Exception{
		String executionid = request.getParameter("executionid");
		String processid = request.getParameter("processid");
		 ActivityImpl wfLeaveImag     = vps.getProcessMap(processid,executionid);  
	        model.addAttribute("wfLeaveImag", wfLeaveImag);  
		
		return "/vaction/graph";
		}

	  
	@RequestMapping("/graphpic")		
	public void getProcessPic(HttpServletRequest request,HttpServletResponse response) throws Exception {  
		String executionid = request.getParameter("executionid");
		InputStream is = 	vps.readResource(executionid); 
		while(is.available()>0){
		response.getOutputStream().write(is.read());
		}
     is.close();
       }  

	@RequestMapping("/complete")		
	public String  completeTasks(HttpServletRequest request){
		String taskId =  request.getParameter("executionId");
		vps.completeTasks(taskId);
		return "redirect:/vaction/myProcess";
	}
}

package net.watermelon.demo.web;

import java.util.ArrayList;
import java.util.List;

import net.watermelon.demo.manager.ActivitiService;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class ActivitiController {

	   @Autowired
	    private ActivitiService myService;
	 
	   @Autowired
	    private TaskService taskService;
	   //开启流程实例
	   @RequestMapping("/process/{personId}/{compId}")
        public void startProcessInstance(@PathVariable Long personId,@PathVariable Long compId) {
	        myService.startProcess(personId,compId);
	    }

	   //获得某个人的任务列表
	   @ResponseBody
	   @RequestMapping("/process/task")
	   public List<TaskRepresentation>   getTasks(String assignee) {
		   List<Task> tasks  =  taskService.createTaskQuery().list();//taskOwner("owner").list();  //taskCandidateUser(assignee).list();
		   List<TaskRepresentation> dtos = new ArrayList<TaskRepresentation>();
	        for (Task task : tasks) {
	            dtos.add(new TaskRepresentation(task.getId(), task.getName()));
	        }
	        return dtos;

	    }
	   
	   
	   
	   
	   //Task的dto
	    static class TaskRepresentation {
	        private String id;
	        private String name;
	        public TaskRepresentation(String id, String name) {
	            this.id = id;
	            this.name = name;
	        }
	        public String getId() {
	            return id;
	        }
	        public void setId(String id) {
	            this.id = id;
	        }
	        public String getName() {
	            return name;
	        }
	        public void setName(String name) {
	            this.name = name;
	        }
	    }
}

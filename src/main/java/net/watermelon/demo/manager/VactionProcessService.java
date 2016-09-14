package net.watermelon.demo.manager;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;


















import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.ScopeImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 一个请假工作流
 * @author samsung
 *
 */
@Service
@Transactional
public class VactionProcessService {
	@Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private RepositoryService repositoryService; 
  //开始流程
    public String startProcess() {
        Map<String, Object> variables = new HashMap<String, Object>();
   //     variables.put("personId", personId);
     //   variables.put("compId", compId);
      return 
    		  runtimeService.startProcessInstanceByKey("myProcess", variables).getId();
       
    }
    
    
  //获得某个人的任务别表
    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }
    
    
  //获得所有的任务别表
    public List<Task> getAllTasks() {
        return taskService.createTaskQuery().list();
    }
    
    //完成任务
    public void completeTasks(Boolean joinApproved, String taskId) {
        Map<String, Object> taskVariables = new HashMap<String, Object>();
        taskVariables.put("joinApproved", joinApproved);
        taskService.complete(taskId, taskVariables);
    }
    
    public  InputStream readResource(String executionId){
       ProcessDefinition pi = repositoryService  
                .createProcessDefinitionQuery().processDefinitionId(executionId)  
                .singleResult();  
        BpmnModel bpmnModel = repositoryService.getBpmnModel(executionId);
        ProcessDiagramGenerator p = new DefaultProcessDiagramGenerator();
        InputStream is = p.generateDiagram(bpmnModel, "png", "宋体", "宋体",   "宋体",null, 1.0);
          return  is ;  

    }

   public   ActivityImpl getProcessMap(String processid , String executionId) throws Exception {  
         ProcessDefinition pi = repositoryService  
               .createProcessDefinitionQuery().processDefinitionId(executionId)  
               .singleResult();  
          ActivityImpl actImpl = null;  
           ExecutionEntity execution = (ExecutionEntity) runtimeService  
                .createExecutionQuery().executionId(processid).singleResult();  
        // 执行实例  
           String processDefinitionId = pi.getId(); 
           
           ProcessDefinitionEntity def = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);

        String activitiId = execution.getActivityId();// 当前实例的执行到哪个节点  
   
        List<ActivityImpl> activitiList =def.getActivities();// 获得当前任务的所有节点  
       
        for (ActivityImpl activityImpl : activitiList) {  
            String id = activityImpl.getId();  
            if (id.equals(activitiId)) {// 获得执行到那个节点  
                actImpl = activityImpl;  
                break;  
            }  
        }  
  
        return actImpl;  
    }  

	
   public void completeTasks(String taskId) {
     //  Map<String, Object> taskVariables = new HashMap<String, Object>();
       //taskVariables.put("joinApproved", joinApproved);
       taskService.complete(taskId, null);
   }
}

package net.watermelon.demo.manager;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;


@Service
public class ProcessService {
	public void joinGroup(DelegateExecution execution){
		System.out.println("OK");
		
	}
}

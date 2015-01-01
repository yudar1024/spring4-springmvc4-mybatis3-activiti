package com.roger.activit.workflow.test;

import java.util.Iterator;
import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-spring.xml")
//@Transactional
public class WorkFlowTest {

	private static Logger loger = LogManager.getLogger("com.roger.loger");
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	TaskService taskService;

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public RuntimeService getRuntimeService() {
		return runtimeService;
	}

	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	@Test
	public void testHellowWorkflow(){
		Deployment deployment = repositoryService.createDeployment().addClasspathResource("com/roger/activiti/workflow/hello.bpmn").deploy();
		loger.info("deploy id = "+deployment.getId());
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("myProcess");
		ProcessInstance processInstance= null;
		processInstance =  runtimeService.createProcessInstanceQuery().processInstanceId(pi.getId()).singleResult();
		if(!processInstance.isEnded()){
			loger.info("流程还在继续");
		}else {
			loger.info("流程已结束");
		}
		List<Task> list  = taskService.createTaskQuery().taskAssignee("张三").list();

		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Task task = (Task) iterator.next();
			loger.info("taskid = "+task.getId() +"task name = "+task.getName());
			taskService.complete(task.getId());
		}
		processInstance=  runtimeService.createProcessInstanceQuery().processInstanceId(pi.getId()).singleResult();
		if(!processInstance.isEnded()){
			loger.info("流程还在继续");
		}else {
			loger.info("流程已结束");
		}
		List<Task> listlisi  = taskService.createTaskQuery().taskAssignee("李四").list();
		for (Iterator iterator = listlisi.iterator(); iterator.hasNext();) {
			Task task = (Task) iterator.next();
			loger.info("taskid = "+task.getId() +"task name = "+task.getName());
			taskService.complete(task.getId());
		}
		
		loger.info(processInstance.isEnded());
		processInstance =  runtimeService.createProcessInstanceQuery().processInstanceId(pi.getId()).singleResult();

		if(processInstance!=null){
			loger.info("流程还在继续");
		}else {
			loger.info("流程已结束");
		}


	}
}

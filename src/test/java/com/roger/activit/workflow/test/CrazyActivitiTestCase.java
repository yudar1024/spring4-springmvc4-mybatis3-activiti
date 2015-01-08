package com.roger.activit.workflow.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.task.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-spring.xml")
public class CrazyActivitiTestCase {
	private static Logger loger = LogManager.getLogger("com.roger.loger");
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	TaskService taskService;

	@Autowired
	ManagementService managementService;

	public ManagementService getManagementService() {
		return managementService;
	}

	public void setManagementService(ManagementService managementService) {
		this.managementService = managementService;
	}

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
	public void testCompensationBoundaryEvent(){
		Deployment deployment = repositoryService.createDeployment()
				.addClasspathResource("com/roger/activiti/workflow/CompensationBoundaryEvent.bpmn")
				.deploy();
		loger.info("deploy id = " + deployment.getId());
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("result", false);
		runtimeService.startProcessInstanceByKey("cbProcess",vars);

	}
	@Test
	public void testSingalThrowingEvent(){
		Deployment deployment = repositoryService.createDeployment()
				.addClasspathResource("com/roger/activiti/workflow/ingalThrowingEvent.bpmn")
				.deploy();
		loger.info("deploy id = " + deployment.getId());
		Map<String, Object> vars = new HashMap<String, Object>();
		runtimeService.startProcessInstanceByKey("stProcess");
		// 完成选择商品任务
				Task firstTask = taskService.createTaskQuery().singleResult();
				taskService.complete(firstTask.getId());
				// 完成用户支付任务
				Task payTask = taskService.createTaskQuery().singleResult();
				taskService.complete(payTask.getId());
				// 由于使用了异步的中间Throwing事件，因此会产生2条工作数据
				List<Job> jobs = managementService.createJobQuery().list();
				System.out.println(jobs.size());
	}
}

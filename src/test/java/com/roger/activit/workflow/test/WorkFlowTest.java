package com.roger.activit.workflow.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-spring.xml")
// @Transactional
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
	@Ignore
	public void testHellowWorkflow() {
		Deployment deployment = repositoryService.createDeployment()
				.addClasspathResource("com/roger/activiti/workflow/hello.bpmn")
				.deploy();
		loger.info("deploy id = " + deployment.getId());
		ProcessInstance pi = runtimeService
				.startProcessInstanceByKey("myProcess");
		ProcessInstance processInstance = null;
		processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(pi.getId()).singleResult();
		isEnded(processInstance);
		List<Task> list = taskService.createTaskQuery().taskAssignee("����")
				.list();

		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Task task = (Task) iterator.next();
			loger.info("taskid = " + task.getId() + "task name = "
					+ task.getName());
			taskService.complete(task.getId());
		}
		processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(pi.getId()).singleResult();
		isEnded(processInstance);
		List<Task> listlisi = taskService.createTaskQuery().taskAssignee("����")
				.list();
		for (Iterator iterator = listlisi.iterator(); iterator.hasNext();) {
			Task task = (Task) iterator.next();
			loger.info("taskid = " + task.getId() + "task name = "
					+ task.getName());
			taskService.complete(task.getId());
		}

		loger.info(processInstance.isEnded());
		processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(pi.getId()).singleResult();

		isEnded(processInstance);
	}

	@Test
	@Ignore
	public void testSequenceFlow() {
		Deployment deployment = repositoryService
				.createDeployment()
				.addClasspathResource(
						"com/roger/activiti/workflow/sequenceflow.bpmn")
				.deploy();
		loger.info("deploy id = " + deployment.getId());
		ProcessInstance pi = runtimeService
				.startProcessInstanceByKey("myProcess");
		ProcessInstance processInstance = null;
		processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(pi.getId()).singleResult();
		List<Task> list = taskService.createTaskQuery().taskAssignee("����")
				.list();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Task task = (Task) iterator.next();
			loger.info("taskid = " + task.getId() + "task name = "
					+ task.getName());
			if (task.getName().equals("���ž�������")) {
				Map<String, Object> varMap = new HashMap<String, Object>();
				varMap.put("message", "no");// message ����Ϊyes ���� no ���߲�ͬ��·��
				taskService.complete(task.getId(), varMap);
			}
		}

		processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstance.getId()).singleResult();
		isEnded(processInstance);
		List<Task> list2 = taskService.createTaskQuery().taskAssignee("����")
				.list();
		for (Iterator iterator = list2.iterator(); iterator.hasNext();) {
			Task task = (Task) iterator.next();
			loger.info("taskid = " + task.getId() + "task name = "
					+ task.getName());
			if (task.getName().equals("�ܾ�������")) {
				taskService.complete(task.getId());
			}
		}
		processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstance.getId()).singleResult();
		isEnded(processInstance);

	}
	@Test
	@Ignore
	public void testExclusiveGetway() {
		Deployment deployment = repositoryService
				.createDeployment()
				.addClasspathResource(
						"com/roger/activiti/workflow/exclusivegetway.bpmn")
				.deploy();
		loger.info("deploy id = " + deployment.getId());
		ProcessInstance pi = runtimeService
				.startProcessInstanceByKey("myProcess");
		ProcessInstance processInstance = null;
		processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(pi.getId()).singleResult();
		Task task = taskService.createTaskQuery().taskAssignee("����").singleResult();
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("money", 400);
		taskService.complete(task.getId(), vars);
		List<Task> tasks = taskService.createTaskQuery().active().list();
		for (Iterator iterator = tasks.iterator(); iterator.hasNext();) {
			Task task2 = (Task) iterator.next();
			loger.info("task id = "+task2.getId()+ " task name =" +task2.getName()+" assignee = "+task2.getAssignee());
		}

		List<Task> managerTasks = taskService.createTaskQuery().taskAssignee("����").list();
		for (Iterator iterator = managerTasks.iterator(); iterator.hasNext();) {
			Task task2 = (Task) iterator.next();
			taskService.complete(task2.getId());

		}
		List<Task> financeTasks = taskService.createTaskQuery().taskAssignee("����").list();
		for (Iterator iterator = financeTasks.iterator(); iterator.hasNext();) {
			Task task3 = (Task) iterator.next();
			taskService.complete(task3.getId());

		}

		loger.info("is end =" +processInstance.isEnded());


		processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstance.getId()).singleResult();
		isEnded(processInstance);

	}

	@Test
	@Ignore
	public void testParallelGetWay(){
		Deployment deployment = repositoryService
				.createDeployment()
				.addClasspathResource(
						"com/roger/activiti/workflow/parallelGetway.bpmn")
				.deploy();
		loger.info("deploy id = " + deployment.getId());
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess");
		showActiveTask(processInstance);
		Task payTask = taskService.createTaskQuery().taskAssignee("������").taskName("����").singleResult();
		taskService.complete(payTask.getId());
		processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstance.getId()).singleResult();
		showActiveTask(processInstance);
		isEnded(processInstance);
		Task fahuoTask = taskService.createTaskQuery().taskAssignee("�̼�").taskName("����").singleResult();
		taskService.complete(fahuoTask.getId());
		processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstance.getId()).singleResult();
		showActiveTask(processInstance);
		isEnded(processInstance);
		Task shoukuanTask = taskService.createTaskQuery().taskAssignee("�̼�").taskName("�տ�").singleResult();
		taskService.complete(shoukuanTask.getId());
		processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstance.getId()).singleResult();
		showActiveTask(processInstance);
		isEnded(processInstance);
		Task shouhuoTask = taskService.createTaskQuery().taskAssignee("������").taskName("�ջ�").singleResult();
		taskService.complete(shouhuoTask.getId());
		processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstance.getId()).singleResult();
		showActiveTask(processInstance);
		isEnded(processInstance);

	}
	@Test
	public void testEvent(){
		Deployment deployment = repositoryService
				.createDeployment()
				.addClasspathResource(
						"com/roger/activiti/workflow/events.bpmn")
				.deploy();
		loger.info("deploy id = " + deployment.getId());
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess");
		Task payTask = taskService.createTaskQuery().taskAssignee("张三").singleResult();
		taskService.complete(payTask.getId());
		processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstance.getId()).singleResult();
		showActiveTask(processInstance);
		isEnded(processInstance);

		Task fahuoTask = taskService.createTaskQuery().taskAssignee("李四").singleResult();
		taskService.complete(fahuoTask.getId());
		processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstance.getId()).singleResult();
		showActiveTask(processInstance);
		isEnded(processInstance);
		runtimeService.signalEventReceived("notice");
		 processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstance.getId()).singleResult();
			showActiveTask(processInstance);
			isEnded(processInstance);

	}



	public void  showActiveTask(ProcessInstance processInstance) {
		if (processInstance!=null) {
			List<Task> activeTasks = taskService.createTaskQuery().processInstanceId(processInstance.getId()).active().list();
			for (Iterator iterator = activeTasks.iterator(); iterator.hasNext();) {
				Task task = (Task) iterator.next();
				loger.info("task id ="+task.getId()+" task name = "+task.getName()+" task assignee = "+task.getAssignee());
			}
			loger.info("=============================");
		}

	}

	public void isEnded(ProcessInstance processInstance){
		if (processInstance != null) {
			loger.info("流程还在继续");
		} else {
			loger.info("流程已结束");
		}
	}

}

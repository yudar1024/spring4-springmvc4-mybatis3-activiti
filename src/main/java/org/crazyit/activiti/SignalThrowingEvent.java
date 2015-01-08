package org.crazyit.activiti;

import java.util.List;

import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.task.Task;

/**
 * 信号中间Throwing事件
 * 
 * @author yangenxiong
 * 
 */
public class SignalThrowingEvent {

	public static void main(String[] args) {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到流程存储服务组件
		RepositoryService repositoryService = engine.getRepositoryService();
		// 得到运行时服务组件
		RuntimeService runtimeService = engine.getRuntimeService();
		// 得到任务服务组件
		TaskService taskService = engine.getTaskService();
		// 得到管理服务组件
		ManagementService managementService = engine.getManagementService();
		// 部署流程文件
		repositoryService.createDeployment()
				.addClasspathResource("bpmn/SignalThrowingEvent.bpmn").deploy();
		// 启动流程
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

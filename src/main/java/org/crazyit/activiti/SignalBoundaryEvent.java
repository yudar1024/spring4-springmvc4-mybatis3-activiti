package org.crazyit.activiti;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

public class SignalBoundaryEvent {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到流程存储服务组件
		RepositoryService repositoryService = engine.getRepositoryService();
		// 得到运行时服务组件
		RuntimeService runtimeService = engine.getRuntimeService();
		// 获取流程任务组件
		TaskService taskService = engine.getTaskService();
		// 部署流程文件
		repositoryService.createDeployment()
				.addClasspathResource("bpmn/SignalBoundaryEvent.bpmn").deploy();
		// 启动2个流程实例
		ProcessInstance pi1 = runtimeService
				.startProcessInstanceByKey("sbProcess");
		ProcessInstance pi2 = runtimeService
				.startProcessInstanceByKey("sbProcess");
		// 查找第一个流程实例中签订合同的任务
		Task pi1Task = taskService.createTaskQuery()
				.processInstanceId(pi1.getId()).singleResult();
		taskService.complete(pi1Task.getId());
		// 查找第二个流程实例中签订合同的任务
		Task pi2Task = taskService.createTaskQuery()
				.processInstanceId(pi2.getId()).singleResult();
		taskService.complete(pi2Task.getId());
		// 此时执行流到达确认合同任务，发送一次信号
		runtimeService.signalEventReceived("contactChangeSignal");
		// 查询全部的任务
		List<Task> tasks = taskService.createTaskQuery().list();
		// 输出结果
		for (Task task : tasks) {
			System.out.println(task.getProcessInstanceId() + "---"
					+ task.getName());
		}
	}

}

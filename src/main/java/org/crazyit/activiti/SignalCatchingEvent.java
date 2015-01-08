package org.crazyit.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;

/**
 * 信号Catching事件
 * @author yangenxiong
 *
 */
public class SignalCatchingEvent {

	public static void main(String[] args) {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到流程存储服务组件
		RepositoryService repositoryService = engine.getRepositoryService();
		// 得到运行时服务组件
		RuntimeService runtimeService = engine.getRuntimeService();
		TaskService taskService = engine.getTaskService();
		// 部署流程文件
		repositoryService.createDeployment()
				.addClasspathResource("bpmn/SignalCatchingEvent.bpmn").deploy();
		// 启动流程
		runtimeService.startProcessInstanceByKey("scProcess");
		Task firstTask = taskService.createTaskQuery().singleResult();
		taskService.complete(firstTask.getId());
		// 此时会出现并行的两个流程分支，查找用户任务并完成
		Task payTask = taskService.createTaskQuery().singleResult();
		// 完成任务
		taskService.complete(payTask.getId());
		// 发送信号完成支付
		runtimeService.signalEventReceived("finishPay");
		Task finishTask = taskService.createTaskQuery().singleResult();
		System.out.println("当前流程任务：" + finishTask.getName());
	}

}

package org.crazyit.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;

/**
 * 错误边界事件
 * @author yangenxiong
 *
 */
public class ErrorBoundaryEvent {

	public static void main(String[] args) {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines
				.getDefaultProcessEngine();
		// 得到流程存储服务组件
		RepositoryService repositoryService = engine.getRepositoryService();
		// 得到运行时服务组件
		RuntimeService runtimeService = engine.getRuntimeService();
		// 获取流程任务组件
		TaskService taskService = engine.getTaskService();
		// 部署流程文件
		repositoryService.createDeployment()
				.addClasspathResource("bpmn/ErrorBoundaryEvent.bpmn").deploy();
		// 启动流程
		runtimeService.startProcessInstanceByKey("ebProcess");
		// 进行任务查询
		Task task = taskService.createTaskQuery().singleResult();
		System.out.println(task.getName());
	}

}

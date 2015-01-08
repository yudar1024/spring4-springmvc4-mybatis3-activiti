package org.crazyit.activiti;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

/**
 * 中间消息Catching事件
 * @author yangenxiong
 *
 */
public class MessageCatchingEvent {

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
				.addClasspathResource("bpmn/MessageCatchingEvent.bpmn").deploy();
		// 启动流程
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("mcProcess");
		Execution exe = runtimeService.createExecutionQuery().activityId("messageintermediatecatchevent1").singleResult();
		runtimeService.messageEventReceived("myMsg", exe.getId());
		// 输出当前的任务
		List<Task> tasks = taskService.createTaskQuery().list();
		for (Task task : tasks) {
			System.out.println(task.getName());
		}
	}

}

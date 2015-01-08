package org.crazyit.activiti;

import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.task.Task;

/**
 * 定时器中间Catching事件
 * @author yangenxiong
 *
 */
public class TimerCatchingEvent {

	public static void main(String[] args) throws Exception {
		// 创建流程引擎
		ProcessEngineImpl engine = (ProcessEngineImpl)ProcessEngines
				.getDefaultProcessEngine();
		// 启动JobExecutor
		engine.getProcessEngineConfiguration().getJobExecutor().start();
		// 得到流程存储服务组件
		RepositoryService repositoryService = engine.getRepositoryService();
		// 得到运行时服务组件
		RuntimeService runtimeService = engine.getRuntimeService();
		TaskService taskService = engine.getTaskService();
		// 部署流程文件
		repositoryService.createDeployment()
				.addClasspathResource("bpmn/TimerCatchingEvent.bpmn").deploy();
		// 启动流程
		runtimeService.startProcessInstanceByKey("tcProcess");
		// 查询当前任务
		Task currentTask = taskService.createTaskQuery().singleResult();
		taskService.complete(currentTask.getId());
		Thread.sleep(1000 * 61);
		// 重新查询当前任务
		currentTask = taskService.createTaskQuery().singleResult();
		System.out.println("定时器中间事件的触发后任务：" + currentTask.getName());
		//关闭JobExecutor
		engine.getProcessEngineConfiguration().getJobExecutor().shutdown();
	}

}

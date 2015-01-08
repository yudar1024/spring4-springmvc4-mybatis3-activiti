package org.crazyit.activiti;

import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.task.Task;

/**
 * 定时器边界事件
 * 
 * @author yangenxiong
 * 
 */
public class TimerBoundaryEvent {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// 创建流程引擎
		ProcessEngineImpl engine = (ProcessEngineImpl) ProcessEngines
				.getDefaultProcessEngine();
		// 开启JobExecutor
		engine.getProcessEngineConfiguration().getJobExecutor().start();
		// 得到流程存储服务组件
		RepositoryService repositoryService = engine.getRepositoryService();
		// 得到运行时服务组件
		RuntimeService runtimeService = engine.getRuntimeService();
		// 获取流程任务组件
		TaskService taskService = engine.getTaskService();
		// 部署流程文件
		repositoryService.createDeployment()
				.addClasspathResource("bpmn/TimerBoundaryEvent.bpmn").deploy();
		// 启动流程
		runtimeService.startProcessInstanceByKey("tbProcess");
		// 查询当前任务
		Task currentTask = taskService.createTaskQuery().singleResult();
		System.out.println("当前处理任务名称：" + currentTask.getName());
		// 停止70秒
		Thread.sleep(1000 * 70);
		// 重新查询当前任务
		currentTask = taskService.createTaskQuery().singleResult();
		System.out.println("当前处理任务名称：" + currentTask.getName());
		// 2分钟后关闭JobExecutor
		Thread.sleep(1000 * 60 * 2);
		engine.getProcessEngineConfiguration().getJobExecutor().shutdown();
	}

}

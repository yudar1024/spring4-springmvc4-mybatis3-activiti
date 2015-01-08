package org.crazyit.activiti;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;

/**
 * 补偿边界事件
 * @author yangenxiong
 *
 */
public class CompensationBoundaryEvent {


	public static void main(String[] args) {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines
				.getDefaultProcessEngine();
		// 得到流程存储服务组件
		RepositoryService repositoryService = engine.getRepositoryService();
		// 得到运行时服务组件
		RuntimeService runtimeService = engine.getRuntimeService();
		// 部署流程文件
		repositoryService.createDeployment()
				.addClasspathResource("com/roger/activiti/workflow/CompensationBoundaryEvent.bpmn").deploy();
		// 初始化参数
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("result", false);
		runtimeService.startProcessInstanceByKey("cbProcess", vars);
	}

}

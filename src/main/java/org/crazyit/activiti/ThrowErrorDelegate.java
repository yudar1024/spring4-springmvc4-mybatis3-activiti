package org.crazyit.activiti;

import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class ThrowErrorDelegate implements JavaDelegate {
	public void execute(DelegateExecution execution) throws Exception {
		String errorCode = "abc";
		System.out.println("抛出错误，errorCode为：" + errorCode);
		throw new BpmnError(errorCode);
	}
}

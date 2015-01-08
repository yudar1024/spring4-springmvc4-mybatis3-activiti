package org.crazyit.activiti;

import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 * 验证转账结果
 * @author yangenxiong
 *
 */
public class ValidateTransferDelegate implements JavaDelegate {
	public void execute(DelegateExecution execution) throws Exception {
		boolean result = (Boolean)execution.getVariable("result");
		if (result) {
			System.out.println("转账成功");
		} else {
			System.out.println("转账失败，抛出错误");
			throw new BpmnError("transferError");
		}
	}
}

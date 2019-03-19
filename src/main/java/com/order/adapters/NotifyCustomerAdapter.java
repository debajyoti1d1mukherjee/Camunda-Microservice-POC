package com.order.adapters;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotifyCustomerAdapter implements JavaDelegate{
	
	 @Autowired
	  protected RabbitTemplate rabbitTemplate;
	 @Autowired
	  private ProcessEngine camunda;

	@Override
	public void execute(DelegateExecution ctx) throws Exception {
	    String result = (String) ctx.getVariable("result");
	    
	    System.out.println("result==============" +" "+result);

	}

}

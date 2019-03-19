package com.order.adapters;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VerifyOrderAmqpAdapter implements JavaDelegate{
	
	 @Autowired
	  protected RabbitTemplate rabbitTemplate;
	 @Autowired
	  private ProcessEngine camunda;

	@Override
	public void execute(DelegateExecution ctx) throws Exception {
	    String custName = (String) ctx.getVariable("custName");
	    String region = (String) ctx.getVariable("region");
	    String paymentTransactionId = (String) ctx.getVariable("paymentTransactionId");
	    System.out.println(custName +" "+region);
	    String msg = region+ ":"+custName+":"+paymentTransactionId ;
	    
       //Place holder to invoke a microservice
	    
	    rabbitTemplate.convertAndSend("orderexchange", "orderroutingkey", msg);
	}

}

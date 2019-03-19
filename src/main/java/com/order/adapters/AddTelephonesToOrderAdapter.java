package com.order.adapters;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.jobexecutor.JobExecutorContext;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;



@Component
@ConfigurationProperties
public class AddTelephonesToOrderAdapter implements JavaDelegate {

  
  @Override
  //@FailingOnLastRetry
  public void execute(DelegateExecution ctx) throws Exception {
    System.out.println("AddTelephonesToOrderAdapter------------------------------------------------");
    String custName = (String) ctx.getVariable("custName");
    String region = (String) ctx.getVariable("region");
    String trunkGroupAdded = (String) ctx.getVariable("trunkGrpAdded");
    System.out.println(custName +" "+region+" "+trunkGroupAdded);
    JobExecutorContext jobExecutorContext = Context.getJobExecutorContext();


    
    //jobExecutorContext.getCurrentJob().setRetries(noOfRetries);
    if ("India".equalsIgnoreCase(region) && jobExecutorContext.getCurrentJob().getRetries()>1) {
        // raise error to be handled in BPMN model in case there was an error in credit card handling
        ctx.setVariable("tnErrorCode", "S100000");
        System.out.println("if jobExecutorContext.getCurrentJob().getRetries()=="+jobExecutorContext.getCurrentJob().getRetries());

        
    	jobExecutorContext.getCurrentJob().setRetries(3);
    	jobExecutorContext.getCurrentJob().insert();

        throw new BpmnError("Error_Telephone");
      }else if("India".equalsIgnoreCase(region) && jobExecutorContext.getCurrentJob().getRetries()<=1){
    	  System.out.println("else jobExecutorContext.getCurrentJob().getRetries()=="+jobExecutorContext.getCurrentJob().getRetries());
    	  throw new BpmnError("Error_NoRetries");
      }
     //Place holder to invoke "AddTelephonesToOrder" microservice

  }


}

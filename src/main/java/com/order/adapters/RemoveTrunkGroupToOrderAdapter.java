package com.order.adapters;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.order.ProcessConstants;

@Component
@ConfigurationProperties
public class RemoveTrunkGroupToOrderAdapter implements JavaDelegate {

  
  @Override
  public void execute(DelegateExecution ctx) throws Exception {
    System.out.println("RemoveTrunkGroupToOrderAdapter Compensation Handler------------------------------------------------");
     //Place holder to invoke the compensation endpoint of microservice
  }


}

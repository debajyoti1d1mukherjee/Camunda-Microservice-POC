package com.order.adapters;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.order.ProcessConstants;

@Component
@ConfigurationProperties
public class GetOrderRequestAdapter implements JavaDelegate {

  @Autowired
  private RestTemplate rest;

  private String restProxyHost;
  private String restProxyPort;

  private String restEndpoint() {
    return "http://" + restProxyHost + ":" + restProxyPort + "/payment/charges";
  }
  
  public static class CreateChargeRequest {
    public int amount; 
  }

  public static class CreateChargeResponse {
    public String transactionId="10000"; 
  }

  @Override
  public void execute(DelegateExecution ctx) throws Exception {
    CreateChargeRequest request = new CreateChargeRequest();
    System.out.println("GET ORDER------------------------------------------------");
    String custName = (String) ctx.getVariable("custName");
    String region = (String) ctx.getVariable("region");
    System.out.println(custName +" "+region);
    //Place holder to invoke a microservice

    
    ctx.setVariable(ProcessConstants.VARIABLE_paymentTransactionId, "10000");

  }

  public String getRestProxyHost() {
    return restProxyHost;
  }

  public void setRestProxyHost(String restProxyHost) {
    this.restProxyHost = restProxyHost;
  }

  public String getRestProxyPort() {
    return restProxyPort;
  }

  public void setRestProxyPort(String restProxyPort) {
    this.restProxyPort = restProxyPort;
  }

}

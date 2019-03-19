package com.order.rest;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.order.ProcessConstants;

@RestController
@RequestMapping("/order")
public class OrderRestController {
  
  @Autowired
  private ProcessEngine camunda;


  
@RequestMapping(method=RequestMethod.POST)
public String placeOrderPOST(@RequestBody  OrderRequest orderReq) {
  System.out.println("orderReq="+orderReq+" "+orderReq.getCustName());	
  placeOrder(orderReq);
  return "Order Request Received";
}

@RequestMapping(method=RequestMethod.GET)
public String placeOrderPOST() {
  return "HELLO CAMUNDA";
}

  /**
   * we need a method returning the {@link ProcessInstance} to allow for easier tests,
   * that's why I separated the REST method (without return) from the actual implementaion (with return value)
   */
  public ProcessInstance placeOrder(OrderRequest orderReq) {
	  System.out.println(orderReq.getCustName()+"   "+orderReq.getRegion());
	  //Invoke Camunda workflow with parameters
    return camunda.getRuntimeService().startProcessInstanceByKey(//
        "order", //
        Variables //
          .putValue("region", orderReq.getRegion()) //
          .putValue("custName", orderReq.getCustName()));
  }
}

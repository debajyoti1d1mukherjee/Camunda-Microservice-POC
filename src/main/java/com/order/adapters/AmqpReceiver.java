package com.order.adapters;

import java.util.UUID;

import org.camunda.bpm.engine.ProcessEngine;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.order.ProcessConstants;

@Component
//@Profile("!test")
public class AmqpReceiver {

  @Autowired
  private ProcessEngine camunda;

  public AmqpReceiver() {
  }
  
  public AmqpReceiver(ProcessEngine camunda) {
    this.camunda = camunda;
  }
  
  /**
   * Dummy method to handle the shipGoods command message - as we do not have a 
   * shipping system available in this small example
   */
  @RabbitListener(bindings = @QueueBinding( //
      value = @Queue(value = "orderqueue", durable = "true"), //
      exchange = @Exchange(value = "orderexchange", type = "direct", durable = "true"), //
      key = "orderroutingkey"))
  @Transactional  
  public void listener(String msg) {
    // and call back directly with a generated transactionId
	  System.out.println("Rabbit Listener:"+msg);
	  handleMsg(msg);
  }

  public void handleMsg(String msg) {
	  String[] msgArray = msg.split(":");
	  String custName = msgArray[1];
	    camunda.getRuntimeService().createMessageCorrelation("message_order") //
        .processInstanceVariableEquals("custName", custName) //
        .setVariable("result", msg) //
        .correlateWithResult();
  }
  
}

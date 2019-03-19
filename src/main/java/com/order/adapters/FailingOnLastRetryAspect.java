package com.order.adapters;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.jobexecutor.JobExecutorContext;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FailingOnLastRetryAspect {
  
  public static String NO_RETRIES_ERROR = "Error_NoRetries";
  
  @Around("@annotation(FailingOnLastRetry)")
  public Object guardedExecute(ProceedingJoinPoint joinPoint) throws Throwable {
    JobExecutorContext jobExecutorContext = Context.getJobExecutorContext();
    System.out.println("jobExecutorContext===="+jobExecutorContext);
    if (jobExecutorContext!=null && jobExecutorContext.getCurrentJob()!=null) {
    	System.out.println("jobExecutorContext.getCurrentJob().getRetries()==="+jobExecutorContext.getCurrentJob().getRetries());
      // this is called from a Job
      if (jobExecutorContext.getCurrentJob().getRetries()<=5) {
        // and the job will run out of retries when it fails again
    	  System.out.println("guardedExecute::<1..........................");
        try {
          return joinPoint.proceed();
        } catch (Exception ex) {
          // Probably save the exception somewhere
        	 System.out.println("guardedExecute::<1..........................will throw exception---");
          throw new BpmnError(NO_RETRIES_ERROR);
        }
      }      
    }else {
    	System.out.println("guardedExecute::<1..........................null");
    }
    // otherwise normal behavior (including retries possibly)
    return joinPoint.proceed();
  }
}
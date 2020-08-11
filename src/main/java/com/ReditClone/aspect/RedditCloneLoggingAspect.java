package com.ReditClone.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class RedditCloneLoggingAspect {
 //setup Logger
private Logger mylogger=Logger.getLogger(getClass().getName());	
	
//setup pointcut declarations for controllers

@Pointcut("execution(* com.ReditClone.controller.*.*(..))")
private void forControllersPackage()
{}


//setup pointcut declarations for services

@Pointcut("execution(* com.ReditClone.services.*.*(..))")
private void forServicesPackage()
{}


//setup pointcut declarations for daos

@Pointcut("execution(* com.ReditClone.repositories.*.*(..))")
private void daosPackage()
{}

// final pointcut for all
@Pointcut("forControllersPackage() || forServicesPackage() || daosPackage() )")
private void forAppFlow()
{}



//add @Before advice
@Before("forAppFlow()")
public void BeforeAdvice(JoinPoint  thejoinpoint)
{   // display the method we are calling....
    String method =	thejoinpoint.getSignature().toShortString();
    mylogger.info("<================in BeforeAdvice Calling method================>"+method);
    
    //display the arguments to the method....
    Object[] args = thejoinpoint.getArgs();
    for(Object tempargs:args)
    {
      mylogger.info("====>argument:"+tempargs);	
    }
 }
	
	
 //add @afterReturning advice
@AfterReturning(
		          pointcut = "forControllersPackage() || forAppFlow() ",
		          returning = "result"
		       )
 public void afterReturning(JoinPoint joinpoint,Object result)
 {
	 //display the method we are calling
	   String method =	joinpoint.getSignature().toShortString();
	   mylogger.info("<=========in After ReturningAdvice calling method=======>"+method);
	 
	 //display the arguments to the method......
	    
     mylogger.info("====>argument:<===="+result);	
	    

	   
 }
	
	
 @AfterThrowing(pointcut = "forControllersPackage() || forAppFlow()", throwing = "iae")
 public void afterThrowing(JoinPoint jointpoint,Exception  iae)
 {
	   //display the method we are calling
	   String method =	jointpoint.getSignature().toShortString();
	   mylogger.info("<=========in After ReturningAdvice calling method=======>"+method);
	   
	   //display the arguments to the method......
	   mylogger.warning("======exception reported by " + jointpoint.getSignature().getName() + " with message : " + iae.getMessage());	

 }
	
}

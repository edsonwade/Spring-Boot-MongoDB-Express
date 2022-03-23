package com.example.springbootmongodbexpress.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before(value = "execution(* *..*Service.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Before: " + joinPoint.getSignature());
    }

    @After(value = "execution(* *..*Service.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("After: " + joinPoint.getSignature());
    }

    @AfterThrowing(value = "execution(* *..*Service.*(..))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        System.out.println("AfterThrowing: " + joinPoint.getSignature() + " " + ex.getMessage());
    }

    @Around(value = "execution(* *..*Service.findAllStudents(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Around Before : " + joinPoint.getSignature());
        Object result;
        result = joinPoint.proceed();
        System.out.println("Around After" + joinPoint.getSignature());
        return result;

    }
}
package com.kai.socialconn.aspects;

import com.kai.socialconn.service.TaskValidator;
import com.kai.socialconn.service.TaskValidatorImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DaoAspect {
   /* @DeclareParents(value = "com.kai.socialconn.service.TaskServiceImpl+", defaultImpl= TaskValidatorImpl.class)
    public TaskValidator taskValidator;*/

    @Pointcut("execution(* com.kai.socialconn.service.TaskServiceImpl.getTaskList(..))")
    public void pointCut(){
        System.out.println("this is daoAspect pointCut.");
    }

    @Before("pointCut()&&args(userId)")
    public void before(JoinPoint jp, String userId) {
        System.out.println("before.....");
        Object[] args = jp.getArgs();
        System.out.println("before....."+userId);
    }

    @After("pointCut()")
    public void after(){
        System.out.println("after.......");
    }

    @AfterReturning("pointCut()")
    public void afterReturning(){
        System.out.println("afterReturning......");
    }

    @AfterThrowing("pointCut()")
    public void afterThrowing(){
        System.out.println("afterThrowing......");
    }

    /*@Around("pointCut()")
    public void around(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("around before......");
        jp.proceed();
        System.out.println("around after......");
    }*/
}

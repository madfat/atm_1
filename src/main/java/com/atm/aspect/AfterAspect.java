package com.atm.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class AfterAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @AfterReturning(value = "execution(* com.atm.service.*.*(..))",
            returning = "result")
    public void afterReturning(JoinPoint joinpoint, Object result){
        logger.info(" {} return with value {}", joinpoint, result);
    }

    @After(value = "execution(* com.atm.service.*.*(..))")
    public void after(JoinPoint joinpoint){
        logger.info(" after performing {}", joinpoint);
    }
}

package com.atm.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class BeforeAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /*
     * What kind of method calls I would intercept
     * execute(* PACKAGE.*.*(..))
     * Weaver & Weaving
     * */
    @Before("execution(* com.atm.service.*.*(..))")
    public void before(JoinPoint joinPoint){
        // advise
        logger.info(" Before performing {}", joinPoint);
    }
}

package com.FireAlarm;


import com.FireAlarm.Constant.OperationType;
import com.FireAlarm.annotation.AutoFill;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Aspect
@Component
public class AutoFillAspect {


    @Pointcut("execution (* com.FireAlarm.Mapper.*.*(..)) && @annotation(com.FireAlarm.annotation.AutoFill)")
    public void autoFullPointcut() {

    }

    @Before("autoFullPointcut()")
    public void autoFill(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        AutoFill autoFill = methodSignature.getMethod().getAnnotation(AutoFill.class);
        Object[] args = joinPoint.getArgs();
        if(args==null||args.length==0){
            return;
        }
        Object object = args[0];
        LocalDateTime now = LocalDateTime.now();
        if(autoFill.value()== OperationType.INSERT||autoFill.value()== OperationType.UPDATE){
            try{
                Method setCreateTime = object.getClass().getDeclaredMethod("setCreateTime", LocalDateTime.class);

                setCreateTime.invoke(object,now);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}

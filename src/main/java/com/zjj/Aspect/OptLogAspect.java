package com.zjj.Aspect;

import com.zjj.annotation.OptLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
public class OptLogAspect {

    // 创建切入点
    @Pointcut("@annotation(com.zjj.annotation.OptLog)")
    public void OptLogPointCut() {}


    @AfterReturning(value = "OptLogPointCut()")
    public void saveOptLog(JoinPoint joinPoint) {
        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取切入点所在的方法
        Method method = signature.getMethod();
        // 获取切入点所在的注解
        OptLog optLog = method.getAnnotation(OptLog.class);
        System.out.println(optLog.optType());
        // 获取被注解的方法的属性
        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        //获取请求的方法名
        String methodName = method.getName();
        //请求的参数
        Object[] args = joinPoint.getArgs();

        // 获取被修饰方法的所有内部参数
        Class<?> clazz= joinPoint.getTarget().getClass();
        Field[] declaredFields = clazz.getDeclaredFields();

        System.out.println(className);  // 注解所在的类
        System.out.println(methodName); // 注解所在的方法
        System.out.println(Arrays.toString(args));  // 外部传递的参数
        System.out.println(Arrays.toString(declaredFields)); // 注解所在类的所有属性

    }

}
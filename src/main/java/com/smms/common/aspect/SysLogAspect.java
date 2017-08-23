package com.smms.common.aspect;

import com.smms.common.util.HttpContextUtils;
import com.smms.common.util.IPUtils;
import com.smms.common.util.JsonUtils;
import com.smms.common.util.ShiroUtils;
import com.smms.modules.sys.entity.SysLog;
import com.smms.modules.sys.entity.SysUser;
import com.smms.modules.sys.service.SysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class SysLogAspect {

    @Autowired
    private SysLogService sysLogService;

    //设置切点:注解
    @Pointcut("@annotation(com.smms.common.annotation.SysLog)")
    public void logPointCut(){

    }

    //切面前存储日志
    @Before("logPointCut()")
    public void saveSysLog(JoinPoint joinPoint){
        //获取切面方法
        MethodSignature signature=(MethodSignature)joinPoint.getSignature();
        Method method=signature.getMethod();

        SysLog sysLog=new SysLog();
        com.smms.common.annotation.SysLog syslog=method.getAnnotation(com.smms.common.annotation.SysLog.class);
        if(sysLog!=null){
            //注解上的描述
            sysLog.setOperation(syslog.value());
        }
        //设置请求的方法名
        String className=joinPoint.getTarget().getClass().getName();
        String methodName=signature.getName();
        sysLog.setMethod(className+"."+methodName);

        //请求的参数
        Object[] args=joinPoint.getArgs();
        String params= JsonUtils.toJson(args[0]);
        sysLog.setParams(params);

        //获取request
        HttpServletRequest requset= HttpContextUtils.getHttpServletRequest();
        //设置IP
        sysLog.setIp(IPUtils.getIpAddr(requset));

        //用户名
        String username= ((SysUser)ShiroUtils.getSubject().getPrincipal()).getUsername();
        sysLog.setUsername(username);

        sysLog.setCreateTime(new Date());

        //保存
        sysLogService.save(sysLog);
    }
}

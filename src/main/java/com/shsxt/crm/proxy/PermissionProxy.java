package com.shsxt.crm.proxy;

import com.shsxt.crm.annotations.RequestPermission;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.utils.AssertUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

/**
 * Created by lp on 2018/1/13.
 */
//@Component
//@Aspect
public class PermissionProxy {

    @Pointcut("@annotation(com.shsxt.crm.annotations.RequestPermission)")
    public  void cut(){};

    @Resource
    private HttpSession session;

   /* @Before(value = "cut()")
    public  void preMethod(MethodSignature methodSignature){
        Method method= methodSignature.getMethod();
        RequestPermission requestPermission= method.getAnnotation(RequestPermission.class);
        System.out.println("权限码值:"+requestPermission.aclValue());
        System.out.println("权限判断。。。");
    }*/

   @Around(value = "cut()")
   public  Object around(ProceedingJoinPoint pjp) throws Throwable {
       // 方法执行前进行拦截
       Object obj=null;
       MethodSignature methodSignature= (MethodSignature) pjp.getSignature();
       Method method= methodSignature.getMethod();
       RequestPermission requestPermission= method.getAnnotation(RequestPermission.class);
       if(null !=requestPermission){
           System.out.println("权限值:"+requestPermission.aclValue());
           List<String> permissions= (List<String>) session.getAttribute(CrmConstant.USER_PERMISSIONS);
           AssertUtil.isTrue(CollectionUtils.isEmpty(permissions),"暂无权限!");
           AssertUtil.isTrue(!(permissions.contains(requestPermission.aclValue())),"暂无权限!");
       }
       obj= pjp.proceed();// 执行目标对象方法
       return  obj;
   }

}

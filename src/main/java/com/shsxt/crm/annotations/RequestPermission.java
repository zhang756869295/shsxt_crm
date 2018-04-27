package com.shsxt.crm.annotations;

import java.lang.annotation.*;

/**
 * Created by lp on 2018/1/13.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestPermission {
    String aclValue();//配置权限码值
}

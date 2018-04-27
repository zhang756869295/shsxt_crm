package com.shsxt.crm.interceptors;

import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.utils.LoginUserUtil;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lp on 2018/1/8.
 */
public class LoginIntercepter extends HandlerInterceptorAdapter {

    @Resource
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Integer userId= LoginUserUtil.releaseUserIdFromCookie(request);
        AssertUtil.isNotLogin(null==userId||null==userService.queryById(userId), CrmConstant.USER_NOT_LOGIN_MSG);
        return true;
    }
}

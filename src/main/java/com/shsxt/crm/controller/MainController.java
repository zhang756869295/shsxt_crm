package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.po.User;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by lp on 2018/1/8.
 */
@Controller
public class MainController  extends BaseController{

    @Resource
    private UserService userService;

    @RequestMapping("main")
public  String main(HttpServletRequest request){
    Integer userId=LoginUserUtil.releaseUserIdFromCookie(request);
    User user= userService.queryById(userId);
    request.setAttribute("user",user);
    return "main";
}
}

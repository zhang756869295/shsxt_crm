package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lp on 2018/1/9.
 */
@Controller
public class TempleController extends BaseController{
    @RequestMapping("test")
    public  String test(){
        return "test";
    }


    @RequestMapping("e01")
    public  String e01(){
        return "e01";
    }

    @RequestMapping("e02")
    public  String e02(){
        return "e02";
    }
}

package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lp on 2018/1/8.
 */
@Controller
public class IndexController extends BaseController{


    @RequestMapping("index")
    public String index(){
        return "index";
    }
}

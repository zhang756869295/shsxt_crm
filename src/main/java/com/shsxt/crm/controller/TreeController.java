package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lp on 2018/1/12.
 */
@Controller
@RequestMapping("tree")
public class TreeController extends BaseController {
    @RequestMapping("tree01")
    public  String index01(){
        return  "tree01";
    }

    @RequestMapping("tree02")
    public  String index02(){
        return  "tree02";
    }

    @RequestMapping("tree03")
    public  String index03(){
        return  "tree03";
    }
}

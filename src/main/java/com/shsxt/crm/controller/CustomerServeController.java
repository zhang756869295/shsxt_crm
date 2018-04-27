package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.po.CustomerServe;
import com.shsxt.crm.query.CustomerServeQuery;
import com.shsxt.crm.service.CustomerServeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by lp on 2018/1/16.
 */
@Controller
@RequestMapping("customerServe")
public class CustomerServeController extends BaseController{

    @Resource
    private CustomerServeService customerServeService;
    @RequestMapping("index/{type}")
    public  String index(@PathVariable Integer type){
        switch (type){
            case 1:
                return "customer_serve_create";
            case 2:
                return "customer_serve_assign";
            case 3:
                return "customer_serve_proce";
            case 4:
                return "customer_serve_feed_back";
            case 5:
                return "customer_serve_archive";
            default:
                return "error";
        }
    }

    @RequestMapping("saveOrUpdateCustomerServe")
    @ResponseBody
    public ResultInfo saveOrUpdateCustomerServe(CustomerServe customerServe){
        customerServeService.saveOrUpdateCustomerServe(customerServe);
        return success(CrmConstant.OPS_SUCCESS_MSG);
    }



    @RequestMapping("queryCustomerServesByParams")
    @ResponseBody
    public Map<String,Object> queryCustomerServesByParams(CustomerServeQuery customerServeQuery,@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer rows){
        customerServeQuery.setPageNum(page);
        customerServeQuery.setPageSize(rows);
        return customerServeService.queryForPage(customerServeQuery);
    }

    @RequestMapping("countCustomerServeByServeType")
    @ResponseBody
    public  Map<String,Object> countCustomerServeByServeType(){
        return customerServeService.countCustomerServeByServeType();
    }
}

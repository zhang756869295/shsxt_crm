package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.po.Customer;
import com.shsxt.crm.query.CustomerQuery;
import com.shsxt.crm.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.xml.transform.Result;
import java.util.Map;

/**
 * Created by lp on 2018/1/15.
 */
@Controller
@RequestMapping("customer")
public class CustomerController extends BaseController {

    @Resource
    private CustomerService customerService;

    @RequestMapping("index")
    public  String index(){
        return "customer";
    }


    @RequestMapping("queryCustomersByParams")
    @ResponseBody
    public Map<String,Object> queryCustomersByParams(CustomerQuery customerQuery, @RequestParam(defaultValue = "1")Integer page,@RequestParam(defaultValue = "10")Integer rows){
        customerQuery.setPageNum(page);
        customerQuery.setPageSize(rows);
        return customerService.queryForPage(customerQuery);
    }


    @RequestMapping("saveOrUpdateCustomer")
    @ResponseBody
    public ResultInfo saveOrUpdateCustomer(Customer customer){
        customerService.saveOrUpdateCustomer(customer);
        return success(CrmConstant.OPS_SUCCESS_MSG);
    }

    @RequestMapping("deleteCustomersByIds")
    @ResponseBody
    public ResultInfo deleteCustomersByIds(Integer[] ids){
        customerService.deleteCustomersByIds(ids);
        return success(CrmConstant.OPS_SUCCESS_MSG);
    }


    @RequestMapping("updateCustomerLossState02")
    @ResponseBody
    public  ResultInfo updateCustomerLossState(){
        customerService.updateCustomerLossState();
        return success(CrmConstant.OPS_SUCCESS_MSG);
    }

    @RequestMapping("countCustomersByLevel")
    @ResponseBody
    public  Map<String,Object> countCustomersByLevel(){
        return customerService.countCustomersByLevel();
    }

}

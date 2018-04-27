package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.po.CustomerLoss;
import com.shsxt.crm.query.CustomerReprieveQuery;
import com.shsxt.crm.service.CustomerLossService;
import com.shsxt.crm.service.CustomerReprieveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by lp on 2018/1/15.
 */
@Controller
@RequestMapping("customerReprieve")
public class CustomerReprieveController extends BaseController {

    @Resource
    private CustomerLossService customerLossService;

    @Resource
    private CustomerReprieveService customerReprieveService;

    @RequestMapping("index")
    public  String index(Integer lossId, Model model){
        CustomerLoss customerLoss=customerLossService.queryById(lossId);
        model.addAttribute("customerLoss",customerLoss);
        return "customer_loss_reprieve";
    }

    @RequestMapping("queryCustomerReprieveByLossId")
    @ResponseBody
    public Map<String,Object> queryCustomerReprieveByLossId(CustomerReprieveQuery customerReprieveQuery, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer rows){
    customerReprieveQuery.setPageNum(page);
    customerReprieveQuery.setPageSize(rows);
    return customerReprieveService.queryForPage(customerReprieveQuery);
    }
}

package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.po.CusDevPlan;
import com.shsxt.crm.po.SaleChance;
import com.shsxt.crm.query.CusDevPlanQuery;
import com.shsxt.crm.service.CusDevPlanService;
import com.shsxt.crm.service.SaleChanceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by lp on 2018/1/9.
 */
@Controller
@RequestMapping("cusDevPlan")
public class CusDevPlanController extends BaseController{

    @Resource
    private SaleChanceService saleChanceService;

    @Resource
    private CusDevPlanService cusDevPlanService;

    @RequestMapping("index")
    public  String index(Integer sid, Model model){
       SaleChance saleChance= saleChanceService.queryById(sid);
       model.addAttribute("saleChance",saleChance);
       return "cus_dev_plan_detail";
    }


    @RequestMapping("queryCusDevPlansBySid")
    @ResponseBody
    public Map<String,Object> queryCusDevPlansBySid(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "10") Integer rows, CusDevPlanQuery cusDevPlanQuery){
        cusDevPlanQuery.setPageNum(page);
        cusDevPlanQuery.setPageSize(rows);
        return cusDevPlanService.queryForPage(cusDevPlanQuery);
    }

    @RequestMapping("saveOrUpdateCusDevPlan")
    @ResponseBody
    public ResultInfo saveOrUpdateCusDevPlan(CusDevPlan cusDevPlan,Integer sid){
        cusDevPlan.setSaleChanceId(sid);
        cusDevPlanService.saveOrUpdateCusDevPlan(cusDevPlan);
        return success(CrmConstant.OPS_SUCCESS_MSG);
    }

    @RequestMapping("delCusDevPlan")
    @ResponseBody
    public ResultInfo delCusDevPlan(Integer[] ids){
        cusDevPlanService.deleteCusDevPlanBatch(ids);
        return success(CrmConstant.OPS_SUCCESS_MSG);
    }
}

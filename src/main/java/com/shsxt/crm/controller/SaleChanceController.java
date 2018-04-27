package com.shsxt.crm.controller;

import com.github.pagehelper.PageInfo;
import com.shsxt.base.BaseController;
import com.shsxt.crm.annotations.RequestPermission;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.po.SaleChance;
import com.shsxt.crm.po.User;
import com.shsxt.crm.query.SaleChanceQuery;
import com.shsxt.crm.service.SaleChanceService;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.LoginUserUtil;
import org.apache.ibatis.annotations.ResultType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by lp on 2018/1/9.
 */
@Controller
@RequestMapping("saleChance")
public class SaleChanceController extends BaseController {
    @Resource
    private SaleChanceService saleChanceService;

    @Resource
    private UserService userService;


   /* @RequestMapping("querySaleChancesByParams")
    @ResponseBody
    public PageInfo<SaleChance> querySaleChancesByParams(SaleChanceQuery saleChanceQuery){
        return saleChanceService.queryByParams(saleChanceQuery);
    }*/



   @RequestMapping("index/{state}")
   public  String index(@PathVariable Integer state){
       if(null!=state){
            if(state==0){
                return "sale_chance";
            }else if(state==1){
                return  "cus_dev_plan";
            }else{
                return "error";
            }
       }else{
           return "error";
       }

   }


   @RequestPermission(aclValue = "101001")
   @RequestMapping("querySaleChancesByParams")
   @ResponseBody
   public Map<String,Object> querySaleChancesByParams(
           @RequestParam(defaultValue = "1") Integer page,
           @RequestParam(defaultValue = "10") Integer rows, SaleChanceQuery saleChanceQuery){
       saleChanceQuery.setPageNum(page);
       saleChanceQuery.setPageSize(rows);
       return saleChanceService.queryForPage(saleChanceQuery);
   }

   @RequestPermission(aclValue = "101002")
   @RequestMapping("saveOrUpdateSaleChance")
   @ResponseBody
   public  ResultInfo saveOrUpdateSaleChance(HttpServletRequest request,SaleChance saleChance){
       Integer userId= LoginUserUtil.releaseUserIdFromCookie(request);
       User user= userService.queryById(userId);
       saleChance.setCreateMan(user.getTrueName());
       saleChanceService.saveOrUpdateSaleChance(saleChance);
       return success(CrmConstant.OPS_SUCCESS_MSG);
   }



   @RequestPermission(aclValue = "101002")
   @RequestMapping("deleteSaleChanceBatch")
    @ResponseBody
    public ResultInfo deleteSaleChanceBatch(Integer[] ids){
        saleChanceService.deleteSaleChanceBatch(ids);
        return success("记录删除成功!");
    }



   @RequestMapping("updateSaleChanceDevResultBySid")
   @ResponseBody
   public  ResultInfo updateSaleChanceDevResultBySid(Integer sid,Integer devResult){
       saleChanceService.updateSaleChanceDevResultBySid(sid,devResult);
       return success("状态更新成功!");
   }
}

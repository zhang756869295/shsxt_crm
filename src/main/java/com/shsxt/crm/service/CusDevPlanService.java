package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.CusDevPlanDao;
import com.shsxt.crm.po.CusDevPlan;
import com.shsxt.crm.po.SaleChance;
import com.shsxt.crm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by lp on 2018/1/11.
 */
@Service
public class CusDevPlanService extends BaseService<CusDevPlan> {

    @Resource
    private CusDevPlanDao cusDevPlanDao;

    @Resource
    private SaleChanceService saleChanceService;





    public  void saveOrUpdateCusDevPlan(CusDevPlan cusDevPlan){
        /**
         * 1.参数校验
         *    计划日期
         *    内容
         *    执行效果
         * 2.根据id 值是否存在判断添加|更新
         *    存在:执行更新
         *       判断记录是否存在  -->执行更新操作
         *     不存在
         *         执行添加
         */
        checkCusDevPlanParams(cusDevPlan.getPlanDate(),cusDevPlan.getPlanItem(),cusDevPlan.getExeAffect());
        cusDevPlan.setUpdateDate(new Date());
        if(null !=cusDevPlan.getId()){

            AssertUtil.isTrue(null==cusDevPlanDao.queryById(cusDevPlan.getId()),"待更新记录不存在或已删除!");
            AssertUtil.isTrue(cusDevPlanDao.update(cusDevPlan)<1,CrmConstant.OPS_FAILED_MSG);
        }else{
            cusDevPlan.setCreateDate(new Date());
            AssertUtil.isTrue(cusDevPlanDao.save(cusDevPlan)<1, CrmConstant.OPS_FAILED_MSG);
            SaleChance saleChance=saleChanceService.queryById(cusDevPlan.getSaleChanceId());
            // 判断开发状态
            if(saleChance.getDevResult().equals(0)){
                saleChance.setDevResult(1);//设置开发状态未开发中
                AssertUtil.isTrue(saleChanceService.update(saleChance)<1,CrmConstant.OPS_FAILED_MSG);
            }
        }
    }

    private void checkCusDevPlanParams(Date planDate, String planItem, String exeAffect) {
        AssertUtil.isTrue(null==planDate,"计划项日期不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(planItem),"请填写计划项内容!");
        AssertUtil.isTrue(StringUtils.isBlank(exeAffect),"执行效果非空!");
    }


    public  void deleteCusDevPlanBatch(Integer[] ids){
        AssertUtil.isTrue(null==ids||ids.length==0,"请选择待删除记录!");
        AssertUtil.isTrue(cusDevPlanDao.deleteBatch(ids)<ids.length,CrmConstant.OPS_FAILED_MSG);
    }
}

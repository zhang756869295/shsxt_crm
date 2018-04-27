package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.SaleChanceDao;
import com.shsxt.crm.po.SaleChance;
import com.shsxt.crm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by lp on 2018/1/9.
 */
@Service
public class SaleChanceService extends BaseService<SaleChance> {

    @Resource
    private SaleChanceDao saleChanceDao;

    public  void saveOrUpdateSaleChance(SaleChance saleChance){
        /**
         * 1.参数校验
         *    参数非空校验
         *    customer_name
         *    link_man
         *    link_phone
         * 2.判断添加|更新操作 唯一标准-id值是否存在
         *    id 存在:执行更新
         *    id 不存在:执行添加
         * 3.判断结果
         */
        checkSaleChanceParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        saleChance.setUpdateDate(new Date());// 设置更新时间
        if(!StringUtils.isBlank(saleChance.getAssignMan())){
            saleChance.setAssignTime(new Date());
            saleChance.setState(1);// 设置为已分配
        }else{
            saleChance.setState(0);// 未分配
        }
        if(null ==saleChance.getId()){
            /**
             * 执行添加
             */
            saleChance.setCreateDate(new Date());
            saleChance.setDevResult(0);// 未开发状态
            AssertUtil.isTrue(saleChanceDao.save(saleChance)<1, CrmConstant.OPS_FAILED_MSG);
        }else{
            /**
             * 执行更新
             *   记录是否存在校验
             */
            AssertUtil.isTrue(null==saleChanceDao.queryById(saleChance.getId()),"待更新记录不存在或已删除!");
            AssertUtil.isTrue(saleChanceDao.update(saleChance)<1,CrmConstant.OPS_FAILED_MSG);
        }

    }

    private void checkSaleChanceParams(String customerName, String linkMan, String linkPhone) {
        AssertUtil.isTrue(StringUtils.isBlank(customerName),"客户名称不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(linkMan),"请提供联系人!");
        AssertUtil.isTrue(StringUtils.isBlank(linkPhone),"请提供联系电话!");
    }

    public  void deleteSaleChanceBatch(Integer[] ids){
        AssertUtil.isTrue(null==ids||ids.length==0,"请选择待删除记录!");
        AssertUtil.isTrue(saleChanceDao.deleteBatch(ids)<ids.length,CrmConstant.OPS_FAILED_MSG);
    }

    public void updateSaleChanceDevResultBySid(Integer sid, Integer devResult) {
        AssertUtil.isTrue(null==sid||null==saleChanceDao.queryById(sid),"待更新营销机会数据不存在!");
        AssertUtil.isTrue(saleChanceDao.updateSaleChanceDevResultBySid(sid,devResult)<1,CrmConstant.OPS_FAILED_MSG);
    }
}

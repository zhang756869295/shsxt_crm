package com.shsxt.crm.dao;

import com.shsxt.base.BaseDao;
import com.shsxt.crm.po.SaleChance;
import org.apache.ibatis.annotations.Param;

/**
 * Created by lp on 2018/1/9.
 */
public interface SaleChanceDao extends BaseDao<SaleChance> {
    public Integer updateSaleChanceDevResultBySid(@Param("sid") Integer sid,@Param("devResult") Integer devResult);
}

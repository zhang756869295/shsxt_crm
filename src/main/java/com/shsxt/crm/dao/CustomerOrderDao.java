package com.shsxt.crm.dao;

import com.shsxt.base.BaseDao;
import com.shsxt.crm.po.CustomerOrder;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface CustomerOrderDao extends BaseDao<CustomerOrder> {

    public Map<String,Object> queryOrderDeatilsByOrderId(@Param("orderId")Integer orderId);

    public  CustomerOrder queryLastOrderByCusId(@Param("cusId") Integer cusId);

}
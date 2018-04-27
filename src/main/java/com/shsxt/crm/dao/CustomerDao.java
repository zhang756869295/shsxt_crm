package com.shsxt.crm.dao;

import com.shsxt.base.BaseDao;
import com.shsxt.crm.dto.CustomerDto;
import com.shsxt.crm.po.Customer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerDao  extends BaseDao<Customer>{

    public  Customer queryCustomerByCusName(@Param("cusName")String cusName);

    public List<Customer> queryLossCustomers();

    public  Integer updateCustomerState(Integer[] ids);


    public  List<CustomerDto> countCustomersByLevel();
}
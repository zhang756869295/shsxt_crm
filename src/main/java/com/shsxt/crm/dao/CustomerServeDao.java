package com.shsxt.crm.dao;

import com.shsxt.base.BaseDao;
import com.shsxt.crm.po.CustomerServe;

import java.util.Map;

public interface CustomerServeDao extends BaseDao<CustomerServe> {

    public Map<String,String> countCustomerServeByServeType();

}
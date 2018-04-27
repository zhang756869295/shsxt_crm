package com.shsxt.crm.dao;

import com.shsxt.base.BaseDao;
import com.shsxt.crm.po.DataDic;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DataDicDao extends BaseDao<DataDic> {

    public List<Map<String,Object>> queryDataDicsByDicName(@Param("dicName")String dicnName);

}
package com.shsxt.crm.dao;

import com.shsxt.base.BaseDao;
import com.shsxt.crm.po.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionDao extends BaseDao<Permission> {

    public  Integer queryPermissionTotalByRoleId(@Param("roleId")Integer roleId);


    public  Integer deletePermissionByRoleId(@Param("roleId")Integer roleId);

    public List<Integer> queryModuleIdsByRoleId(@Param("roleId")Integer roleId);

    

}
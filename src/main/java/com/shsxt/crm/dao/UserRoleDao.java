package com.shsxt.crm.dao;

import com.shsxt.base.BaseDao;
import com.shsxt.crm.po.UserRole;

public interface UserRoleDao extends BaseDao<UserRole>{

    public  Integer queryUserRoleTotalByUserId(Integer userId);

    public  Integer deleteUserRoleByUserId(Integer userId);

    public  Integer queryUserRoleTotalByRoleId(Integer roleId);

    public  Integer deleteUserRoleByRoleId(Integer roleId);


    public  Integer deleteUserRoleByUserIds(Integer[] userIds);

}
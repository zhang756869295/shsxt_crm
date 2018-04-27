package com.shsxt.crm.dao;

import com.shsxt.base.BaseDao;
import com.shsxt.crm.po.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface RoleDao  extends BaseDao<Role> {

    public List<Role> queryAllRoles();

    @Select("select id,role_name as roleName,role_remark as roleRemark ,create_date as createDate," +
            "update_date as updateDate from t_role where role_name=#{roleName} and is_valid=1")
    public  Role queryRoleByRoleName(@Param("roleName")String roleName);


    public  List<String> queryAllPermissionsByRoleIds(List<Integer> roleIds);



}
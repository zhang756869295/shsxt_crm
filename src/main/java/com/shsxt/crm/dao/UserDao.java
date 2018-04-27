package com.shsxt.crm.dao;

import com.shsxt.base.BaseDao;
import com.shsxt.crm.dto.UserDto;
import com.shsxt.crm.po.User;
import com.shsxt.crm.query.UserQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by lp on 2018/1/8.
 */
public interface UserDao extends BaseDao<User> {
    public User queryUserByUserName(@Param("userName") String userName);


    public  Integer updateUserPasswordById(@Param("userId") Integer userId,@Param("userPwd") String userPwd);


    public List<Map<String,Object>> queryCustomerManagers();

    public  List<UserDto> queryUsersByParams(UserQuery userQuery);

    public  List<Integer> queryAllRoleIdsByUserId(@Param("userId") Integer userId);
}


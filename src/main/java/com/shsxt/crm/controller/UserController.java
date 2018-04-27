package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dto.UserDto;
import com.shsxt.crm.exceptions.ParamsException;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.model.UserModel;
import com.shsxt.crm.po.User;
import com.shsxt.crm.query.UserQuery;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lp on 2018/1/8.
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;



    @RequestMapping("index")
    public String index(){
        return "user";
    }

    @RequestMapping("userLogin")
    @ResponseBody
    public ResultInfo userLogin(String userName, String userPwd, HttpSession session){
        UserModel userModel= userService.userLoginCheck(userName,userPwd);
        List<String> permissions= userService.queryUserHasPermissions(userName);
        session.setAttribute(CrmConstant.USER_PERMISSIONS,permissions);//将权限值放入session
        return success("用户登录成功",userModel);
    }

    @RequestMapping("updateUserPassword")
    @ResponseBody
    public  ResultInfo updateUserPassword(String oldPassword, String newPassword, String confirmPassword, HttpServletRequest request){
     Integer userId= LoginUserUtil.releaseUserIdFromCookie(request);
     userService.updateUserPasswordByUserId(userId,oldPassword,newPassword,confirmPassword);
     return success("密码更新成功!");
    }

    @RequestMapping("queryCustomerManagers")
    @ResponseBody
    public List<Map<String,Object>> queryCustomerManagers(){
        return userService.queryCustomerManagers();
    }


    @RequestMapping("queryUsersByParams")
    @ResponseBody
    public  Map<String ,Object> queryUsersByParams(UserQuery userQuery, @RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "10") Integer rows){
        userQuery.setPageNum(page);
        userQuery.setPageSize(rows);
        return userService.queryUsersByParams(userQuery);
    }


    @RequestMapping("saveOrUpdateUser")
    @ResponseBody
    public  ResultInfo saveOrUpdateUser(UserDto userDto){
        List<Integer> roleIds= userDto.getRoleIds();
        /**
         *  去空元素
         */
        List<Integer> target=null;
        if(!CollectionUtils.isEmpty(roleIds)){
            target=new ArrayList<Integer>();
            for(Integer roleId:roleIds){
                if(null !=roleId){
                    target.add(roleId);
                }
            }
            userDto.setRoleIds(target);
        }
        userService.saveOrUpdateUser(userDto);
        return success(CrmConstant.OPS_SUCCESS_MSG);
    }


    @RequestMapping("deleteUserBatch")
    @ResponseBody
    public  ResultInfo deleteUserBatch(Integer[] ids){
        userService.deleteUserBatch(ids);
        return success(CrmConstant.OPS_SUCCESS_MSG);
    }
}

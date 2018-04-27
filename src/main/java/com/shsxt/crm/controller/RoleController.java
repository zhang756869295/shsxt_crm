package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.po.Role;
import com.shsxt.crm.query.RoleQuery;
import com.shsxt.crm.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.xml.transform.Result;
import java.util.List;
import java.util.Map;

/**
 * Created by lp on 2018/1/12.
 */
@Controller
@RequestMapping("role")
public class RoleController extends BaseController {
    @Resource
    private RoleService roleService;


    @RequestMapping("index")
    public  String index(){
        return "role";
    }

    @RequestMapping("queryAllRoles")
    @ResponseBody
    public List<Role> queryAllRoles(){
        return  roleService.queryAllRoles();
    }

    @RequestMapping("queryRolesByParams")
    @ResponseBody
    public Map<String,Object> queryRolesByParams(RoleQuery roleQuery, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer rows){
        roleQuery.setPageNum(page);
        roleQuery.setPageSize(rows);
        return  roleService.queryForPage(roleQuery);
    }


    @RequestMapping("saveOrUpdateRole")
    @ResponseBody
    public ResultInfo saveOrUpdateRole(Role role){
        roleService.saveOrUpdateRole(role);
        return  success(CrmConstant.OPS_SUCCESS_MSG);
    }


    @RequestMapping("deleteRolesBatch")
    @ResponseBody
    public ResultInfo deleteRolesBatch(Integer[] ids){
        roleService.deleteRolesBatch(ids);
        return  success(CrmConstant.OPS_SUCCESS_MSG);
    }


    @RequestMapping("addGrant")
    @ResponseBody
    public ResultInfo addGrant(Integer rid,Integer[] moduleIds){
        roleService.addGrant(rid,moduleIds);
        return success("角色授权成功");
    }
 }


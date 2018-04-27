package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.ModuleDao;
import com.shsxt.crm.dto.ModuleDto;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.po.Module;
import com.shsxt.crm.query.ModuleQuery;
import com.shsxt.crm.service.ModuleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by lp on 2018/1/12.
 */
@Controller
@RequestMapping("module")
public class ModuleController extends BaseController{
    @Resource
    private ModuleService moduleService;



    @RequestMapping("index")
    public  String index(){
        return "module";
    }

    @RequestMapping("queryAllModules")
    @ResponseBody
    public List<ModuleDto> queryAllModules(Integer rid){
        return moduleService.queryAllModules(rid);
    }


    @RequestMapping("queryModulesByParams")
    @ResponseBody
    public Map<String,Object> queryModulesByParams(ModuleQuery moduleQuery, @RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "10") Integer rows){
        moduleQuery.setPageNum(page);
        moduleQuery.setPageSize(rows);
        return moduleService.queryForPage(moduleQuery);
    }


    @RequestMapping("saveOrUpdateModule")
    @ResponseBody
    public ResultInfo saveOrUpdateModule(Module module){
        moduleService.saveOrUpdateModule(module);
        return success(CrmConstant.OPS_SUCCESS_MSG);
    }


    @RequestMapping("queryAllParentMenusByGrade")
    @ResponseBody
    public  List<Map<String,Object>> queryAllParentMenusByGrade(Integer grade){
        return moduleService.queryAllParentMenusByGrade(grade);
    }


    @RequestMapping("deleteModuleByMid")
    @ResponseBody
    public  ResultInfo deleteModuleByMid(Integer ids){
        moduleService.deleteModuleByMid(ids);
        return success(CrmConstant.OPS_SUCCESS_MSG);
    }
}

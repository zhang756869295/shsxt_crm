package com.shsxt.crm.dao;

import com.shsxt.base.BaseDao;
import com.shsxt.crm.dto.ModuleDto;
import com.shsxt.crm.po.Module;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ModuleDao extends BaseDao<Module> {

    public List<ModuleDto> queryAllModules();

    public  Module queryModuleByModuleName(@Param("moduleName")String moduleName);

    public  Module queryModuleByOptValue(@Param("optValue")String optValue);

    public List<Map<String,Object>> queryAllParentMenusByGrade(@Param("grade") Integer grade);

    public  List<Module> querySubModulesByParentId(@Param("pid") Integer pid);



}
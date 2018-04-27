package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.ModuleDao;
import com.shsxt.crm.dao.PermissionDao;
import com.shsxt.crm.dto.ModuleDto;
import com.shsxt.crm.po.Module;
import com.shsxt.crm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by lp on 2018/1/12.
 */
@Service
public class ModuleService extends BaseService<Module> {
    @Resource
    private ModuleDao moduleDao;
    @Resource
    private PermissionDao permissionDao;

    public List<ModuleDto> queryAllModules(){
        return moduleDao.queryAllModules();
    }

    public List<ModuleDto> queryAllModules(Integer rid){
        /**
         * 1.根据角色id 查询分配的资源id 集合
         * rid=1      moduleIds01={1,2,3,4,5}
         * 2.查询所有资源
         *    moduleIds02={1,2,3,4,5,6,7,8,9..}
         * 3.判断moduleIds02 集合中是否包含moduIds01 集合中元素
         */

        List<Integer> moduleIds= permissionDao.queryModuleIdsByRoleId(rid);
        List<ModuleDto> moduleDtos= moduleDao.queryAllModules();
        if(!CollectionUtils.isEmpty(moduleDtos)){
            if(!CollectionUtils.isEmpty(moduleIds)){
                    for(ModuleDto moduleDto:moduleDtos){
                        if(moduleIds.contains(moduleDto.getId())){
                            moduleDto.setChecked(true);//当前角色拥有操作该菜单权限
                        }
                    }
            }
        }

        return moduleDtos;
    }


    public  void saveOrUpdateModule(Module module){
        /**
         * 1.参数校验
         *    模块名 非空  唯一
         *    parent_id
         *       根级菜单  parentId=null
         *       一级菜单以及二级菜单  parentId 必须提供
         *    grade 非空 0|1|2
         *    optValue 非空 唯一
         * 2.添加|更新
         */
        checkModuleParams(module.getModuleName(),module.getParentId(),module.getGrade(),module.getOptValue(),module.getId());
        module.setUpdateDate(new Date());
        if(null!=module.getId()){
            AssertUtil.isTrue(moduleDao.update(module)<1,CrmConstant.OPS_FAILED_MSG);
        }else{
            module.setCreateDate(new Date());
            module.setIsValid((byte) 1);
            AssertUtil.isTrue(moduleDao.save(module)<1, CrmConstant.OPS_FAILED_MSG);
        }
    }

    private void checkModuleParams(String moduleName, Integer parentId, Integer grade, String optValue,Integer mid) {
        AssertUtil.isTrue(StringUtils.isBlank(moduleName),"模块名非空!");
        /**
         * grade
         *   null  根级菜单
         *   !null 子级菜单
         */
        if(null!=parentId){
            AssertUtil.isTrue(null==moduleDao.queryById(parentId),"父级菜单不存在!");
        }
        AssertUtil.isTrue(null==grade,"请选择菜单层级!");
        AssertUtil.isTrue(grade!=0&&grade!=1&&grade!=2,"层级值不合法!");
        if(grade.equals(1)||grade.equals(2)){
            AssertUtil.isTrue(null==parentId||null==moduleDao.queryById(parentId),"请指定父级菜单!");
        }
        AssertUtil.isTrue(StringUtils.isBlank(optValue),"权限码非空!");
        Module module=null;
        Module module02=null;
        module=moduleDao.queryModuleByModuleName(moduleName);
        module02=moduleDao.queryModuleByOptValue(optValue);
        if(null!=mid){
            Module temp=moduleDao.queryById(mid);
            AssertUtil.isTrue(null==temp,"待更新的记录不存在!");
            AssertUtil.isTrue(null!=module&&!(module.getId().equals(temp.getId())),"该模块名已存在!");
            AssertUtil.isTrue(null!=module02&&!(module02.getId().equals(temp.getId())),"该权限值已存在!");
        }else{
            /**
             * 添加校验
             */
            AssertUtil.isTrue(null!=module,"该模块名已存在!");
            AssertUtil.isTrue(null!=module02,"该权限值已存在!");
        }



    }

    public List<Map<String,Object>> queryAllParentMenusByGrade(Integer grade) {
        return moduleDao.queryAllParentMenusByGrade(grade);
    }

    public  void deleteModuleByMid(Integer mid){
        AssertUtil.isTrue(null==mid||null==moduleDao.queryById(mid),CrmConstant.OPS_FAILED_MSG);
        List<Integer> mids=new ArrayList<Integer>();
        mids=getAllModuleIds(mid,mids);
        Integer[] ids=new Integer[mids.size()];
        if(!CollectionUtils.isEmpty(mids)){
            for(int i=0;i<mids.size();i++){
                ids[i]=mids.get(i);
            }
           AssertUtil.isTrue(moduleDao.deleteBatch(ids)<ids.length,CrmConstant.OPS_FAILED_MSG);
        }

    }

    private List<Integer> getAllModuleIds(Integer mid, List<Integer> mids) {
        Module module=moduleDao.queryById(mid);
        if(null!=module){
            mids.add(module.getId());
            List<Module> modules=moduleDao.querySubModulesByParentId(module.getId());
            if(!CollectionUtils.isEmpty(modules)){
                for(Module temp:modules){
                    mids= getAllModuleIds(temp.getId(),mids);
                }
            }
        }
        return mids;
    }


}

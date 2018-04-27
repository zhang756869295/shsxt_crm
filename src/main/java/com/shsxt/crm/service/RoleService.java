package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.ModuleDao;
import com.shsxt.crm.dao.PermissionDao;
import com.shsxt.crm.dao.RoleDao;
import com.shsxt.crm.dao.UserRoleDao;
import com.shsxt.crm.po.Module;
import com.shsxt.crm.po.Permission;
import com.shsxt.crm.po.Role;
import com.shsxt.crm.utils.AssertUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionOperations;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by lp on 2018/1/12.
 */
@Service
public class RoleService extends BaseService<Role> {
    @Resource
    private RoleDao roleDao;

    @Resource
    private UserRoleDao userRoleDao;

    @Resource
    private PermissionDao permissionDao;

    @Resource
    private ModuleDao moduleDao;


    public List<Role> queryAllRoles(){
        return roleDao.queryAllRoles();
    }


    public  void saveOrUpdateRole(Role role){
        /**
         * 1.参数非空校验
         *     角色名称非空
         * 2.角色唯一性校验
         * 3.添加|更新
         */
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"角色名称不能为空!");
        Role temp=roleDao.queryRoleByRoleName(role.getRoleName());
        role.setUpdateDate(new Date());
        if(null!=role.getId()){
            AssertUtil.isTrue(null!=temp&&!(temp.getId().equals(role.getId())),"角色名称不能重复!");
            AssertUtil.isTrue(roleDao.update(role)<1,CrmConstant.OPS_FAILED_MSG);
        }else{
            /**
             * 添加
             */
            AssertUtil.isTrue(null!=temp,"角色已存在!");
            role.setCreateDate(new Date());
            role.setIsValid(1);
            AssertUtil.isTrue(roleDao.save(role)<1, CrmConstant.OPS_FAILED_MSG);
        }
    }


    public  void deleteRolesBatch(Integer[] ids){
        /**
         * 1.删除从表 t_user_role 对应记录
         * 2.删除角色记录
         */
        AssertUtil.isTrue(null==ids||ids.length==0,"请选择待删除角色记录!");
        for(Integer roleId:ids){
            Integer total=userRoleDao.queryUserRoleTotalByRoleId(roleId);
            if(total>0){
                AssertUtil.isTrue(userRoleDao.deleteUserRoleByRoleId(roleId)<total,CrmConstant.OPS_FAILED_MSG);
            }
        }
        AssertUtil.isTrue(roleDao.deleteBatch(ids)<ids.length,CrmConstant.OPS_FAILED_MSG);

    }


    /**
     * 执行授权
     * @param rid   角色id
     * @param moduleIds 模块id
     */
    public  void addGrant(Integer rid,Integer[] moduleIds){
        AssertUtil.isTrue(null==rid||rid==0||null== roleDao.queryById(rid),"请选择角色记录!");
        /**
         * moduleIds==null   清空角色权限
         */
        /**
         * 第一次授权
         *    直接添加即可
         * 第二次授权
         *    先执行删除 删除角色原始权限 再添加最新的权限
         */
        Integer total=permissionDao.queryPermissionTotalByRoleId(rid);
        if(total>0){
            AssertUtil.isTrue(permissionDao.deletePermissionByRoleId(rid)<total,CrmConstant.OPS_FAILED_MSG);
        }
        if(ArrayUtils.isNotEmpty(moduleIds)){
            /**
             * 批量添加资源到权限表
             */
            List<Permission> permissions=new ArrayList<Permission>();
            for(Integer moduleId:moduleIds){
                 Permission permission=new Permission();
                 permission.setCreateDate(new Date());
                 permission.setModuleId(moduleId);
                 permission.setRoleId(rid);
                 permission.setUpdateDate(new Date());
                 Module module=moduleDao.queryById(moduleId);
                 permission.setAclValue(module.getOptValue());
                 permissions.add(permission);
            }
            AssertUtil.isTrue(permissionDao.saveBatch(permissions)<moduleIds.length,CrmConstant.OPS_FAILED_MSG);
        }

    }


    public  List<String> queryAllPermissionsByRoleIds(List<Integer> roleIds){
        AssertUtil.isTrue(null==roleIds||roleIds.size()==0,"角色不存在!");
        return roleDao.queryAllPermissionsByRoleIds(roleIds);
    }




}

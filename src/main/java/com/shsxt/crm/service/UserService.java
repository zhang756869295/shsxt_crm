package com.shsxt.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shsxt.base.BaseService;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.UserDao;
import com.shsxt.crm.dao.UserRoleDao;
import com.shsxt.crm.dto.UserDto;
import com.shsxt.crm.model.UserModel;
import com.shsxt.crm.po.User;
import com.shsxt.crm.po.UserRole;
import com.shsxt.crm.query.UserQuery;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.utils.Md5Util;
import com.shsxt.crm.utils.UserIDBase64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by lp on 2018/1/8.
 */
@Service
public class UserService extends BaseService<User>{

    @Resource
    private UserDao userDao;

    @Resource
    private UserRoleDao userRoleDao;

    @Resource
    private RoleService roleService;

    public UserModel userLoginCheck(String userName,String userPwd){
        /**
         * 1.参数校验
         * 2.查询用户记录存在
         *   存在
         *      校验密码(加密)
         *        比对成功  用户登录成功 返回用户信息
         *        比对失败   密码不正确
         *   不存在
         *      用户登录非法
         */
        checkUserLoginParams(userName,userPwd);
        User user=userDao.queryUserByUserName(userName);
        AssertUtil.isTrue(null==user,"改用户不存在或已注销!");
        AssertUtil.isTrue(!user.getUserPwd().equals( Md5Util.encode(userPwd)),"密码不正确!");
        return buildUserModelInfo(user);
    }

    private UserModel buildUserModelInfo(User user) {
        UserModel userModel=new UserModel();
        userModel.setUserName(user.getUserName());
        userModel.setTrueName(user.getTrueName());
        userModel.setUserIdStr(UserIDBase64.encoderUserID(user.getId()));
        return userModel;
    }

    private void checkUserLoginParams(String userName, String userPwd) {
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(userPwd),"密码不能为空!");
    }

    /**
     * 密码更新
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     */
    public  void updateUserPasswordByUserId(Integer userId,String oldPassword,String newPassword,String confirmPassword){
        /**
         * 1.参数校验
         *      userId  记录必须存在
         *      oldPassword 必须与数据库中密码一致
         *      newPassword   confirmPassword  非空  必须相同
         * 2.执行更新
         *     新密码 加密（md5）
         *     执行更新 判断结果
         */
        checkUpdatePasswordParams(userId,oldPassword,newPassword,confirmPassword);
        AssertUtil.isTrue(userDao.updateUserPasswordById(userId,Md5Util.encode(newPassword))<1, CrmConstant.OPS_FAILED_MSG);
    }

    private void checkUpdatePasswordParams(Integer userId, String oldPassword, String newPassword, String confirmPassword) {
        User user=userDao.queryById(userId);
        AssertUtil.isTrue(null==userId||null==user,"更新用户不存在!");
        AssertUtil.isTrue(StringUtils.isBlank(oldPassword),"原始密码非空!");
        AssertUtil.isTrue(!user.getUserPwd().equals(Md5Util.encode(oldPassword)),"原始密码不正确!");
        AssertUtil.isTrue(StringUtils.isBlank(newPassword)||StringUtils.isBlank(confirmPassword),"新密码不能为空!");
        AssertUtil.isTrue(!newPassword.equals(confirmPassword),"新密码不一致!");
    }


    public List<Map<String,Object>> queryCustomerManagers(){
        return userDao.queryCustomerManagers();
    }



    public  Map<String,Object> queryUsersByParams(UserQuery userQuery){
        PageHelper.startPage(userQuery.getPageNum(),userQuery.getPageSize());
        List<UserDto> entities=userDao.queryUsersByParams(userQuery);
        if(!CollectionUtils.isEmpty(entities)){
            for(UserDto userDto:entities){
                String roleIdStr=userDto.getRoleIdStr();
                if(!StringUtils.isBlank(roleIdStr)){
                    String[] temp=roleIdStr.split(",");
                    for(String roleId:temp){
                        userDto.getRoleIds().add(Integer.parseInt(roleId));
                    }
                }
            }
        }
        PageInfo<UserDto> pageInfo=new PageInfo<UserDto>(entities);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("total",pageInfo.getTotal());
        map.put("rows",pageInfo.getList());
        return map;
    }


    public  void saveOrUpdateUser(UserDto userDto){
        /**
         * 1.参数判断
         *   用户名  密码(统一:123456)  trueName email phone 非空
         * 2.用户记录唯一性校验
         *    用户名不能重复
         * 3.执行添加|更新
         *    userId
         *      存在  执行更新
         *      不存在  执行添加
         * 4.角色id 处理(用户角色分配)
         *    执行用户添加操作
         *       批量添加用户角色记录到用户角色表
         *    执行用户更新操作
         *       原始 1 2 3
         *       最新
         *        1 2 3 14
         *        1 3
         *        null
         *      1.比对
         *         有可能执行记录添加   也有可能执行删除
         *     2.先执行删除  后执行添加
         */
        checkUserParams(userDto.getUserName(),userDto.getTrueName(),userDto.getEmail(),userDto.getPhone());
        User temp=userDao.queryUserByUserName(userDto.getUserName());
        /**
         *  添加
         *  null==temp  用户名合法
         *  null!=temp  用户名不合法  用户 名重复
         * 更新
         *  null==temp  shsxt-->shsxt00001  合法
         *  null !=temp
         *     shsxt-->shsxt  合法
         *     shsxt-->admin  不合法
         */
        userDto.setUpdateDate(new Date());
        User user=new User();
        Integer key=null;
        if(null !=userDto.getId()){
            /**
             * 执行更新
             */
            AssertUtil.isTrue(null!=temp&&!(temp.getId().equals(userDto.getId())),"该用户已存在!");
            BeanUtils.copyProperties(userDto,user);
            AssertUtil.isTrue(userDao.update(user)<1,CrmConstant.OPS_FAILED_MSG);
            key=user.getId();
        }else{
            /**
             * 执行添加
             */
            AssertUtil.isTrue(null!=temp,"该用户已存在!");
            userDto.setCreateDate(new Date());
            BeanUtils.copyProperties(userDto,user);
            user.setUserPwd(Md5Util.encode("123456"));//密码统一123456
            AssertUtil.isTrue(userDao.save(user)<1,CrmConstant.OPS_FAILED_MSG);
            key=user.getId();//获取主键
        }

        // 关联用户角色-角色分配操作
        relationUserRoleInfo(key,userDto.getRoleIds());


    }

    /**
     * 角色分配实现
     * @param userId
     * @param roleIds
     */
    private void relationUserRoleInfo(Integer userId, List<Integer> roleIds) {
        /**
         *    1.执行删除(删除用户角色原始记录) 根据用户id 执行删除操作
         *    2.执行批量添加(集合内容存在)
         */
        Integer total= userRoleDao.queryUserRoleTotalByUserId(userId);
        if(total>0){
            // 用户角色存在
            AssertUtil.isTrue(userRoleDao.deleteUserRoleByUserId(userId)<total,CrmConstant.OPS_FAILED_MSG);
        }
        List<UserRole> userRoles=null;
        if(!CollectionUtils.isEmpty(roleIds)) {
            userRoles = new ArrayList<UserRole>();
            for (Integer roleId : roleIds) {
                    UserRole userRole = new UserRole();
                    userRole.setUserId(userId);
                    userRole.setRoleId(roleId);
                    userRole.setCreateDate(new Date());
                    userRole.setUpdateDate(new Date());
                    userRoles.add(userRole);
            }
            AssertUtil.isTrue(userRoleDao.saveBatch(userRoles)<roleIds.size(),CrmConstant.OPS_FAILED_MSG);
        }


    }

    private void checkUserParams(String userName, String trueName, String email, String phone) {
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(trueName),"真实名称不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(email),"邮箱不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(phone),"手机号不能为空!");
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public  void deleteUserBatch(Integer[] ids){
        AssertUtil.isTrue(null==ids||ids.length==0,"请选择删除的用户记录!");
        /**
         * 1.删除用户记录
         * 2.删除用户角色记录
         *   根据userId
         */
       /* AssertUtil.isTrue(userRoleDao.deleteUserRoleByUserIds(ids)<ids.length,CrmConstant.OPS_FAILED_MSG);*/
       //userRoleDao.deleteUserRoleByUserIds(ids);
        for(Integer userId:ids){
            Integer total=userRoleDao.queryUserRoleTotalByUserId(userId);
            if(total>0){
                AssertUtil.isTrue(userRoleDao.deleteUserRoleByUserId(userId)<total,CrmConstant.OPS_FAILED_MSG);
            }
        }
        int a=1/0;
       AssertUtil.isTrue(userDao.deleteBatch(ids)<ids.length,CrmConstant.OPS_FAILED_MSG);
    }


    /**
     * 用户拥有的所有权限
     * @param userName
     * @return
     */
    public  List<String> queryUserHasPermissions(String userName){
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名非空!");
        User user= userDao.queryUserByUserName(userName);
        AssertUtil.isTrue(null==user,"该用户不存在!");
        // 查询用户拥有的所有角色
        List<Integer> roleIds=userDao.queryAllRoleIdsByUserId(user.getId());
        return roleService.queryAllPermissionsByRoleIds(roleIds);
    }

}

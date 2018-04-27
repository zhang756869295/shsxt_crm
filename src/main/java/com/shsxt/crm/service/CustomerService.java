package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.CustomerDao;
import com.shsxt.crm.dao.CustomerLossDao;
import com.shsxt.crm.dao.CustomerOrderDao;
import com.shsxt.crm.dto.CustomerDto;
import com.shsxt.crm.po.Customer;
import com.shsxt.crm.po.CustomerLoss;
import com.shsxt.crm.po.CustomerOrder;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.utils.MathUtil;
import com.sun.corba.se.impl.protocol.giopmsgheaders.CancelRequestMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by lp on 2018/1/15.
 */
@Service
public class CustomerService extends BaseService<Customer> {

    @Resource
    private CustomerDao customerDao;


    @Resource
    private CustomerOrderDao customerOrderDao;


    @Resource
    private CustomerLossDao customerLossDao;


    public  void saveOrUpdateCustomer(Customer customer){
        /**
         * 1.参数校验
         *   name 非空  唯一性
         *   cusManager 非空
         *   phone 非空
         *   fr 非空
         * 2.执行添加|更新
         *    id 是否空
         */
        AssertUtil.isTrue(StringUtils.isBlank(customer.getName()),"客户名称非空!");
        AssertUtil.isTrue(StringUtils.isBlank(customer.getCusManager()),"客户经理非空!");
        AssertUtil.isTrue(StringUtils.isBlank(customer.getPhone()),"联系方式非空!");
        AssertUtil.isTrue(StringUtils.isBlank(customer.getFr()),"法人代表不能为空!");
        Customer temp=customerDao.queryCustomerByCusName(customer.getName());
        customer.setUpdateDate(new Date());
        if(null!=customer.getId()){
            AssertUtil.isTrue(null!=temp&&!(temp.getId().equals(customer.getId())),"客户已存在!");
            AssertUtil.isTrue(customerDao.update(customer)<1,CrmConstant.OPS_FAILED_MSG);
        }else{
            AssertUtil.isTrue(null !=temp,"客户已存在!");
            customer.setIsValid(1);
            customer.setState(0);// 未流失
            customer.setCreateDate(new Date());
            customer.setKhno(MathUtil.genereateKhCode());// 设置客户编号
            AssertUtil.isTrue(customerDao.save(customer)<1, CrmConstant.OPS_FAILED_MSG);
        }
    }


    public  void deleteCustomersByIds(Integer[] ids){
        AssertUtil.isTrue(null==ids||ids.length==0,"请选择待删除记录!");
        AssertUtil.isTrue(customerDao.deleteBatch(ids)<ids.length,CrmConstant.OPS_FAILED_MSG);
    }



    public  void updateCustomerLossState(){
        List<Customer> lossCustomers=customerDao.queryLossCustomers();
        Integer[] cids=null;
        List<CustomerLoss> customerLosses=null;
        if(!CollectionUtils.isEmpty(lossCustomers)){
            cids=new Integer[lossCustomers.size()];
            customerLosses=new ArrayList<CustomerLoss>();
            for(Integer i=0;i<lossCustomers.size();i++){
                Customer customer=lossCustomers.get(i);
                cids[i]=customer.getId();
                CustomerLoss customerLoss=new CustomerLoss();
                customerLoss.setCreateDate(new Date());
                customerLoss.setCusManager(customer.getCusManager());
                customerLoss.setCusNo(customer.getKhno());
                customerLoss.setCusName(customer.getName());
                customerLoss.setIsValid(1);
                /*
                   查询最后下单日期
                 */
                CustomerOrder customerOrder= customerOrderDao.queryLastOrderByCusId(customer.getId());
                if(null!=customerOrder){
                    customerLoss.setLastOrderTime(customerOrder.getOrderDate());
                }
                customerLoss.setState(0);// 暂缓流失状态
                customerLoss.setUpdateDate(new Date());
                customerLosses.add(customerLoss);
            }
            // 执行批量更新操作  批量更新客户流失状态   批量添加客户流失数据
            AssertUtil.isTrue(customerDao.updateCustomerState(cids)< cids.length,CrmConstant.OPS_FAILED_MSG);
            AssertUtil.isTrue(customerLossDao.saveBatch(customerLosses)<customerLosses.size(),CrmConstant.OPS_FAILED_MSG);
        }
    }



    public Map<String,Object> countCustomersByLevel(){
        List<CustomerDto> customerDtos= customerDao.countCustomersByLevel();
        Map<String,Object> map=new HashMap<String,Object>();
        Integer[] data1=null;
        String[] data2=null;
        if(!CollectionUtils.isEmpty(customerDtos)){
            data1=new Integer[customerDtos.size()];
            data2=new String[customerDtos.size()];
            for(int i=0;i<customerDtos.size();i++){
                data1[i]=customerDtos.get(i).getTotal();
                data2[i]=customerDtos.get(i).getLevel();
            }
            map.put("data1",data1);
            map.put("data2",data2);
            map.put("code",200);
            map.put("msg","查询成功");
        }else{
            map.put("code",300);
            map.put("msg","记录不存在");
        }
        return map;
    }
}

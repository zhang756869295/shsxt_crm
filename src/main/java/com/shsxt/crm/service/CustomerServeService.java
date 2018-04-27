package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.CustomerServeDao;
import com.shsxt.crm.dto.CustomerServeDto;
import com.shsxt.crm.enums.CustomerServeStatus;
import com.shsxt.crm.po.CustomerServe;
import com.shsxt.crm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by lp on 2018/1/16.
 */
@Service
public class CustomerServeService extends BaseService<CustomerServe> {

    @Resource
    private CustomerServeDao customerServeDao;


    public  void saveOrUpdateCustomerServe(CustomerServe customerServe){
        /**
         * 1.参数校验
         *     服务类型  客户名称
         *     overview
         *     serveRequest
         * 2.执行添加
         */
        checkCustomerServeParams(customerServe.getServeType(),customerServe.getCustomer(),customerServe.getOverview(),customerServe.getServiceRequest());
        customerServe.setUpdateDate(new Date());
        if(null!=customerServe.getId()){
            AssertUtil.isTrue(null==customerServeDao.queryById(customerServe.getId()),"待更新的记录不存在!");
            if(customerServe.getState().equals(CustomerServeStatus.SERVE_ASSIGN.getType())){
                /**
                 * 执行分配操作
                 */
                AssertUtil.isTrue(StringUtils.isBlank(customerServe.getAssigner()),"请指定分配人!");
                customerServe.setAssignTime(new Date());//设置分配时间
            }
            if(customerServe.getState().equals(CustomerServeStatus.SERVE_PROCE.getType())){
                /**
                 * 执行服务处理
                 */
                AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceProcePeople()),"处理人非空!");
                AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceProce()),"处理内容非空!");
                customerServe.setServiceProceTime(new Date());
            }
            if(customerServe.getState().equals(CustomerServeStatus.SERVE_FEED_BACK.getType())){
                AssertUtil.isTrue(StringUtils.isBlank(customerServe.getMyd()),"满意度非空!");
                customerServe.setState(CustomerServeStatus.SERVE_ARCHIVE.getType());
            }
            AssertUtil.isTrue(customerServeDao.update(customerServe)<1,CrmConstant.OPS_FAILED_MSG);
        }else{
            customerServe.setCreateDate(new Date());
            customerServe.setState(CustomerServeStatus.SERVE_CREATE.getType());
            AssertUtil.isTrue(customerServeDao.save(customerServe)<1, CrmConstant.OPS_FAILED_MSG);
        }
    }

    private void checkCustomerServeParams(String serveType, String customer, String overview, String serviceRequest) {
        AssertUtil.isTrue(StringUtils.isBlank(serveType),"请选择服务类型!");
        AssertUtil.isTrue(StringUtils.isBlank(customer),"请输入客户名称!");
        AssertUtil.isTrue(StringUtils.isBlank(overview),"概要信息非空!");
        AssertUtil.isTrue(StringUtils.isBlank(serviceRequest),"请求内容非空!");
    }


    public Map<String,Object> countCustomerServeByServeType(){
        Map<String,String> map= customerServeDao.countCustomerServeByServeType();
        Map<String,Object> target=new HashMap<String,Object>();
        CustomerServeDto[] customerServeDtos=null;
        if(null!=map&&!map.isEmpty()){
            String[] strs=map.get("dicValueStr").split(",");
            String[] totalStrs=map.get("totalStr").split(",");
            Integer[] totals=toIntegerArray(totalStrs);
            target.put("data1",strs);
            customerServeDtos=new CustomerServeDto[strs.length];
            for(Integer j=0;j<totals.length;j++){
               CustomerServeDto customerServeDto=new CustomerServeDto();
               customerServeDto.setName(strs[j]);
               customerServeDto.setValue(totals[j]);
               customerServeDtos[j]=customerServeDto;
            }
            target.put("data2",customerServeDtos);

            target.put("code",200);
            target.put("msg","查询成功");
        }else{
            target.put("code",300);
            target.put("msg","记录不存在!");
        }
        return target;
    }

    private Integer[] toIntegerArray(String[] totalStrs) {
        Integer[] arry=new Integer[totalStrs.length];
        for(Integer i=0;i<totalStrs.length;i++){
            arry[i]=Integer.parseInt(totalStrs[i]);
        }

        return arry;
    }
}

package com.shsxt.crm.tasks;

import com.shsxt.crm.service.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lp on 2018/1/15.
 */
@Service
public class TaskService {

    @Resource
    private CustomerService customerService;

    public  void job01(){
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        customerService.updateCustomerLossState();
    }
}

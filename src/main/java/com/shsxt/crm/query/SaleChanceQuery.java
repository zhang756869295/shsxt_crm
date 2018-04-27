package com.shsxt.crm.query;

import com.shsxt.base.BaseQuery;

import java.util.Date;

/**
 * Created by lp on 2018/1/9.
 */
public class SaleChanceQuery extends BaseQuery {
    private String customerName;//客户名称
    private Integer state;// 分配状态
    private Integer devResult;//开发结果
    private String time;//创建时间

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getDevResult() {
        return devResult;
    }

    public void setDevResult(Integer devResult) {
        this.devResult = devResult;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

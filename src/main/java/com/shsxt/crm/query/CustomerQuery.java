package com.shsxt.crm.query;

import com.shsxt.base.BaseQuery;

/**
 * Created by lp on 2018/1/15.
 */
public class CustomerQuery extends BaseQuery {
    private String khno;
    private String cusName;
    private String fr;


    public String getKhno() {
        return khno;
    }

    public void setKhno(String khno) {
        this.khno = khno;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getFr() {
        return fr;
    }

    public void setFr(String fr) {
        this.fr = fr;
    }
}

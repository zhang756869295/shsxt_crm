package com.shsxt.crm.enums;

/**
 * Created by lp on 2018/1/16.
 */
public enum CustomerServeStatus {
    SERVE_CREATE("1"),// 服务创建
    SERVE_ASSIGN("2"),//  服务分配
    SERVE_PROCE("3"),// 服务处理
    SERVE_FEED_BACK("4"),// 服务反馈
    SERVE_ARCHIVE("5");// 服务归档
    private String  type;


    CustomerServeStatus(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

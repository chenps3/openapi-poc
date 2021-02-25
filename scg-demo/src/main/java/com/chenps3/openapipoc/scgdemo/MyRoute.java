package com.chenps3.openapipoc.scgdemo;

import lombok.Data;

@Data
public class MyRoute {

    private String id;

    /**
     * 协议 http dubbo
     */
    private String protocol;

    /**
     * 路由匹配路径
     */
    private String path;

    /**
     * dubbo接口
     */
    private String dubboInterface;

    /**
     * dubbo版本号
     */
    private String dubboVersion;

    /**
     * dubbo方法
     */
    private String dubboMethod;

    /**
     * 转发地址
     */
    private String uri;
}

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
     * 服务id
     */
    private String service;

    /**
     * 服务版本号
     */
    private String version;

    /**
     * 转发地址
     */
    private String uri;

    /**
     *
     */
    private Integer priority;
}

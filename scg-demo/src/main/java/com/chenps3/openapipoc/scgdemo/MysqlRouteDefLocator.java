package com.chenps3.openapipoc.scgdemo;

import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class MysqlRouteDefLocator implements RouteDefinitionLocator {

    @Resource
    private MyRouteMapper myRouteMapper;

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        return Flux.fromIterable(myRouteMapper.listAll().stream()
                .map(route -> {
                    RouteDefinition routeDefinition = new RouteDefinition();
                    List<FilterDefinition> filterDefinitions = new ArrayList<>();
                    List<PredicateDefinition> predicateDefinitions = new ArrayList<>();
                    //dubbo协议需要添加请求头
                    if ("dubbo".equals(route.getProtocol())) {
                        //添加AddRequestHeader Filter
                        FilterDefinition f1 = new FilterDefinition();
                        f1.setName("AddRequestHeader");
                        Map<String, String> m1 = new HashMap<>();
                        m1.put("name", "dubbo-interface");
                        m1.put("value", route.getDubboInterface());
                        f1.setArgs(m1);
                        filterDefinitions.add(f1);

                        FilterDefinition f2 = new FilterDefinition();
                        f2.setName("AddRequestHeader");
                        Map<String, String> m2 = new HashMap<>();
                        m2.put("name", "dubbo-version");
                        m2.put("value", route.getDubboVersion());
                        f2.setArgs(m2);
                        filterDefinitions.add(f2);

                        FilterDefinition f3 = new FilterDefinition();
                        f3.setName("AddRequestHeader");
                        Map<String, String> m3 = new HashMap<>();
                        m3.put("name", "dubbo-method");
                        m3.put("value", route.getDubboMethod());
                        f3.setArgs(m3);
                        filterDefinitions.add(f3);
                    }
                    URI uri = UriComponentsBuilder.fromUriString(route.getUri()).build().toUri();

                    //添加SetPath Filter重写路径，默认情况下，转发到uri时路径会保留
                    FilterDefinition setPath = new FilterDefinition();
                    setPath.setName("SetPath");
                    Map<String, String> setPathArgs = new HashMap<>(1);
                    setPathArgs.put("template", uri.getPath());
                    setPath.setArgs(setPathArgs);
                    filterDefinitions.add(setPath);

                    //只根据path匹配
                    PredicateDefinition p = new PredicateDefinition("Path=" + route.getPath());
                    predicateDefinitions.add(p);

                    routeDefinition.setId(route.getId());
                    routeDefinition.setPredicates(predicateDefinitions);
                    routeDefinition.setFilters(filterDefinitions);
                    routeDefinition.setUri(uri);
                    return routeDefinition;
                }).collect(Collectors.toList()));
    }
}

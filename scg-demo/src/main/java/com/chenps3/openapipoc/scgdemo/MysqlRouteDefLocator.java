package com.chenps3.openapipoc.scgdemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class MysqlRouteDefLocator implements RouteDefinitionLocator {

//    @Resource
//    private MyRouteMapper myRouteMapper;


    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        return Flux.fromIterable(listAllV1().stream()
                .map(route -> {
                    RouteDefinition routeDefinition = new RouteDefinition();
                    List<FilterDefinition> filterDefinitions = new ArrayList<>();
                    List<PredicateDefinition> predicateDefinitions = new ArrayList<>();
                    //dubbo协议需要重写请求头
                    if ("dubbo".equals(route.getProtocol())) {
                        //添加AddRequestHeader过滤器
                        FilterDefinition f1 = new FilterDefinition();
                        f1.setName("AddRequestHeader");
                        Map<String, String> m1 = new HashMap<>();
                        m1.put("name", "service-id");
                        m1.put("value", route.getService());
                        f1.setArgs(m1);
                        filterDefinitions.add(f1);

                        FilterDefinition f2 = new FilterDefinition();
                        f2.setName("AddRequestHeader");
                        Map<String, String> m2 = new HashMap<>();
                        m2.put("name", "version");
                        m2.put("value", route.getVersion());
                        f2.setArgs(m2);
                        filterDefinitions.add(f2);
                    }
                    //只根据path匹配
                    PredicateDefinition p = new PredicateDefinition();
                    p.setName("Path");
                    Map<String, String> m = new HashMap<>();
                    m.put("pattern", route.getPath());
                    p.setArgs(m);
                    predicateDefinitions.add(p);
                    routeDefinition.setId(route.getId());
                    routeDefinition.setPredicates(predicateDefinitions);
                    routeDefinition.setFilters(filterDefinitions);
                    routeDefinition.setOrder(route.getPriority());
                    routeDefinition.setUri(UriComponentsBuilder.fromUriString(route.getUri()).build().toUri());
                    return routeDefinition;
                }).collect(Collectors.toList()));
    }

    private List<MyRoute> listAllV1(){
        MyRoute r1 = new MyRoute();
        r1.setId("test001");
        r1.setProtocol("http");
        r1.setPath("/test001");
        r1.setUri("localhost:9999/httptest001");
        r1.setPriority(1);
        MyRoute r2 = new MyRoute();
        r2.setId("test002");
        r2.setProtocol("dubbo");
        r2.setPath("/test002");
        r2.setUri("localhost:9999/dubbo");
        r2.setPriority(2);
        r2.setService("com.chenps3.dubbotest002");
        r2.setVersion("1.0.0");
        return Arrays.asList(r1,r2);
    }

//    private List<MyRoute> listAllV2(){
//        return myRouteMapper.listAll();
//    }
}

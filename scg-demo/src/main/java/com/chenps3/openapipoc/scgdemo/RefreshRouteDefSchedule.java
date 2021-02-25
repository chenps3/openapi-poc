package com.chenps3.openapipoc.scgdemo;

import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RefreshRouteDefSchedule implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    /**
     * 每5秒刷新一次路由配置
     */
    @Scheduled(fixedDelay = 5000L)
    public void job() {
        publisher.publishEvent(new RefreshRoutesEvent(this));
    }
}

package com.chenps3.openapipoc.dubboproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@SpringBootApplication
@RestController
public class DubboProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboProxyApplication.class, args);
    }

    @GetMapping("/http/test/v1")
    public Object test001(HttpServletRequest httpServletRequest) {
        return "helloV1";
    }

    @GetMapping("/http/test/v2")
    public Object test002(HttpServletRequest httpServletRequest) {
        return "helloV2";
    }

    @GetMapping("/dubbo")
    public Map<String, String> dubbo(HttpServletRequest httpServletRequest) {
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        Map<String, String> m = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = httpServletRequest.getHeader(headerName);
            m.put(headerName, headerValue);
        }
        return m;
    }
}

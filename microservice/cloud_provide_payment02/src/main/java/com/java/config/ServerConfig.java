package com.java.config;

import lombok.Getter;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ServerConfig implements ApplicationListener<WebServerInitializedEvent> {
    private Integer port;


    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.port = event.getSource().getPort();
    }
}

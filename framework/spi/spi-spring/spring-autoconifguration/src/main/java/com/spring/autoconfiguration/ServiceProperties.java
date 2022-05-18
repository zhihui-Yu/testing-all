package com.spring.autoconfiguration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author simple
 */
@ConfigurationProperties(prefix = "spring.service")
public class ServiceProperties {
    private boolean enable;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}

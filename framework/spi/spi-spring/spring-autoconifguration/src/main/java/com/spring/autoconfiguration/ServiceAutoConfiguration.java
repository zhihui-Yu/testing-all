package com.spring.autoconfiguration;

import com.spring.service.api.ServiceApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author simple
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ServiceProperties.class)
public class ServiceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(ServiceApi.class)
    public ServiceApi serviceApi() {
        // 可以注入一个feign client来实现分离接口
        return new ServiceApi();
    }
}

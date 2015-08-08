package com.rjrudin.marklogic.junit.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.rjrudin.marklogic.client.spring.BasicConfig;

@Configuration
@PropertySource({ "file:gradle.properties" })
public class BasicTestConfig extends BasicConfig {

    @Value("${mlTestRestPort:0}")
    private Integer mlTestRestPort;

    @Override
    protected Integer getRestPort() {
        return (mlTestRestPort != null && mlTestRestPort > 0) ? mlTestRestPort : getMlRestPort();
    }

    public Integer getMlTestRestPort() {
        return mlTestRestPort;
    }

    @Override
    protected String buildContentDatabaseName(String mlAppName) {
        return mlAppName + "-test-content";
    }

}
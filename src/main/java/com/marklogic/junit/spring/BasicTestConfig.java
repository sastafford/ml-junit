package com.marklogic.junit.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.marklogic.client.modulesloader.ModulesLoader;
import com.marklogic.client.modulesloader.impl.DefaultModulesLoader;
import com.marklogic.client.modulesloader.impl.XccAssetLoader;
import com.marklogic.client.spring.BasicConfig;

/**
 * Extends BasicConfig (from ml-javaclient-util) with test-specific properties.
 */
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

    /**
     * This is included by default so that ModulesLoaderTestExecutionListener can be used.
     * 
     * @return
     */
    @Bean
    public ModulesLoader modulesLoader() {
        return new DefaultModulesLoader(xccAssetLoader());
    }

    /**
     * Makes some assumptions about how to connect via XCC to load modules - feel free to override in a subclass.
     * 
     * @return
     */
    @Bean
    public XccAssetLoader xccAssetLoader() {
        XccAssetLoader l = new XccAssetLoader();
        l.setUsername(getMlUsername());
        l.setPassword(getMlPassword());
        l.setHost(getMlHost());
        l.setPort(8000);
        l.setDatabaseName(getMlAppName() + "-modules");
        return l;
    }

    @Override
    protected String buildContentDatabaseName(String mlAppName) {
        return mlAppName + "-test-content";
    }

}
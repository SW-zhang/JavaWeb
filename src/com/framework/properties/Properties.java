package com.framework.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * @author wang
 * @create 2017-03-15 15:30
 *
 * 读取配置文件类，可以配置多个配置文件
 * 使用时 跟service 一样注入调用即可
 **/
@Configuration
@PropertySources({
        @PropertySource("classpath:application.properties")
})
@Service
public class Properties {

    @Autowired
    Environment env;

    public String getValue(String key) {
        return env.getProperty(key);
    }

    public String getValue(String key, String defaultValue) {
        return env.getProperty(key, defaultValue);
    }

    public <T> T getValue(String key, Class<T> targetType) {
        return env.getProperty(key, targetType);
    }

    public <T> T getValue(String key, Class<T> targetType, T defaultValue) {
        return env.getProperty(key, targetType, defaultValue);
    }
}

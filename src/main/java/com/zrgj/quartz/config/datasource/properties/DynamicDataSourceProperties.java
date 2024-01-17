package com.zrgj.quartz.config.datasource.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author 肘劉祁
 * 动态数据源配置
 */

@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource.dynamic")
public class DynamicDataSourceProperties {

    /**
     * 数据源配置数据
     */
    private Map<String, DataSourceProperties> datasource;
}

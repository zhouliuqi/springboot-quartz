package com.zrgj.quartz.config.datasource.properties;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author 肘劉祁
 * 定时任务数据源配置
 */
@Data
@Component
public class DataSourceProperties {

    /**
     * 数据库连接url
     */
    private String url;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;


    /**
     * 驱动名称
     */
    private String driverClassName;

    /**
     *  使用druid数据源
     */
    private String type;

}

package com.zrgj.quartz.config.datasource.annotion;

import com.zrgj.quartz.config.datasource.DataSourceType;

import java.lang.annotation.*;

/**
 * @author 肘劉祁
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {

    /**
     * 切换数据源名称
     */
    DataSourceType value() default DataSourceType.MASTER;

}

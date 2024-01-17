package com.zrgj.quartz.config.datasource;

/**
 * @author 肘劉祁
 * 数据源类型枚举
 */
public enum  DataSourceType {

    /**
     * 主库
     */
    MASTER,

    /**
     * 定时任务库
     */
    QUARTZ,

    /**
     * 流程库
     */
    ACTIVITY
}

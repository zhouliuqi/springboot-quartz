package com.zrgj.quartz.config.datasource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author 肘劉祁
 * 动态数据源
 */

public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 定义一个 ThreadLocal 变量，用于在每个线程中保存数据源的标识
     */
    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 构造方法
     * @param defaultTargetDataSource 默认数据源
     * @param targetDataSources 数据源集合
     */
    public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources) {
        //设置默认数据源
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        //设置数据源集合
        super.setTargetDataSources(targetDataSources);
        //调用父类方法完成数据源初始化
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        //获取数据源标识
        return getDataSource();
    }

    /**
     * 设置数据源标识
     * @param dataSource 数据源标识
     */
    public static void setDataSource(String dataSource) {
        THREAD_LOCAL.set(dataSource);
    }

    /**
     * 获取数据源标识
     * @return 数据源标识
     */
    public static String getDataSource() {
        return THREAD_LOCAL.get();
    }


    /**
     * 删除数据源标识
     */
    public static void clearDataSource() {
        THREAD_LOCAL.remove();
    }

}
package com.zrgj.quartz.config.datasource;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.zrgj.quartz.config.datasource.properties.DataSourceProperties;
import com.zrgj.quartz.config.datasource.properties.DruidProperties;
import com.zrgj.quartz.config.datasource.properties.DynamicDataSourceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 肘劉祁
 * 数据源配置类
 */

@Configuration
@Slf4j
public class DataSourceConfig {

    @Resource
    private DynamicDataSourceProperties dynamicDataSourceProperties;

    @Resource
    private DruidProperties druidProperties;


    @Bean
    @Primary
    public DataSource masterDataSource() throws SQLException {
        //数据源类型
        DataSourceType sourceType = DataSourceType.MASTER;
        //初始化数据源bean
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        //设置数据源
        ds.setXaDataSource(druidXADataSource(sourceType));
        //设置唯一资源名称
        ds.setUniqueResourceName(sourceType.name()+"_DB");
        //连接池数
        ds.setPoolSize(druidProperties.getInitialSize());
        return ds;
    }

    @Bean
    public DataSource quartzDataSource() throws SQLException {
        //数据源类型
        DataSourceType sourceType = DataSourceType.QUARTZ;
        //初始化数据源bean
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        //设置数据源
        ds.setXaDataSource(druidXADataSource(sourceType));
        //设置唯一资源名称
        ds.setUniqueResourceName(sourceType.name()+"_DB");
        //连接池数
        ds.setPoolSize(druidProperties.getInitialSize());
        return ds;
    }

    @Bean
    public DataSource activityDataSource() throws SQLException {
        //数据源类型
        DataSourceType sourceType = DataSourceType.ACTIVITY;
        //初始化数据源bean
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        //设置数据源
        ds.setXaDataSource(druidXADataSource(sourceType));
        //设置唯一资源名称
        ds.setUniqueResourceName(sourceType.name()+"_DB");
        //连接池数
        ds.setPoolSize(druidProperties.getInitialSize());
        return ds;
    }



    /**
     * 获取数据源
     * @param dataSourceType 数据源类型
     * @return 数据源
     * @throws SQLException 异常
     */
    public DruidXADataSource druidXADataSource(DataSourceType dataSourceType) throws SQLException {
        //初始化对象
        DruidXADataSource xaDataSource = new DruidXADataSource();
        //获取数据库配置数据
        DataSourceProperties dataSourceProperties = dynamicDataSourceProperties.getDatasource().get(dataSourceType.name());
        //连接地址
        xaDataSource.setUrl(dataSourceProperties.getUrl());
        //账号
        xaDataSource.setUsername(dataSourceProperties.getUsername());
        //密码
        xaDataSource.setPassword(dataSourceProperties.getPassword());
        //驱动类
        xaDataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
        //用于校验连接是否有效的 SQL 查询语句
        xaDataSource.setValidationQuery(druidProperties.getValidationQuery());
        //初始化连接池大小
        xaDataSource.setInitialSize(druidProperties.getInitialSize());
        //连接池保持的最小空闲连接数
        xaDataSource.setMinIdle(druidProperties.getMinIdle());
        //连接池中同时活动的最大连接数
        xaDataSource.setMaxActive(druidProperties.getMaxActive());
        //获取连接时最大等待时间，单位毫秒
        xaDataSource.setMaxWait(druidProperties.getMaxWait());
        //连接池中连接在空闲状态下最小的生存时间，超过该时间会被关闭，单位是毫秒
        xaDataSource.setMinEvictableIdleTimeMillis(druidProperties.getMinEvictableIdleTimeMillis());
        //连接池中连接在空闲状态下最大的生存时间，超过该时间会被关闭，单位是毫秒
        xaDataSource.setMaxEvictableIdleTimeMillis(druidProperties.getMaxEvictableIdleTimeMillis());
        //配置间隔多久进行一次检测，检测需要关闭的空闲连接，单位毫秒。 默认是60s，太长可能会导致无法及时检测到连接中断
        xaDataSource.setTimeBetweenEvictionRunsMillis(druidProperties.getTimeBetweenEvictionRunsMillis());
        //是否在获取连接时执行测试查询，用于检测连接是否有效
        xaDataSource.setTestOnBorrow(druidProperties.getTestOnBorrow());
        //是否在连接空闲时执行测试查询，用于检测连接是否有效
        xaDataSource.setTestWhileIdle(druidProperties.getTestWhileIdle());
        //否在归还连接时执行测试查询，用于检测连接是否有效
        xaDataSource.setTestOnReturn(druidProperties.getTestOnReturn());
        //配置是否定期探活
        xaDataSource.setKeepAlive(druidProperties.getKeepAlive());
        //配置探活间隔 默认120s
        xaDataSource.setKeepAliveBetweenTimeMillis(druidProperties.getKeepAliveBetweenTimeMillis());
        //配置一个连接最大使用次数，避免长时间使用相同连接造成服务器端负载不均衡
        xaDataSource.setPhyMaxUseCount(druidProperties.getPhyMaxUseCount());
        //可以在连接关闭时记录日志
        xaDataSource.setLogAbandoned(druidProperties.getLogAbandoned());
        //开启缓存 可以提升性能
        xaDataSource.setPoolPreparedStatements(druidProperties.getPoolPreparedStatements());
        //stat 可用于统计监控连接池的活动情况，wall 可用于防止 SQL 注入攻击，slf4j 可用于记录连接池的日志
        xaDataSource.setFilters(druidProperties.getFilters());
        return xaDataSource;
    }


    /**
     * 注册数据源
     * @return 数据源
     */
    @Bean
    public DynamicDataSource dynamicDataSource() throws SQLException {
        //初始化数据源
        Map<Object, Object> targetDataSourcesMap = new HashMap<>(3);
        //主数据源
        targetDataSourcesMap.put(DataSourceType.MASTER.name(), masterDataSource());
        //从数据源
        targetDataSourcesMap.put(DataSourceType.QUARTZ.name(), quartzDataSource());
        //第三个数据源
        targetDataSourcesMap.put(DataSourceType.ACTIVITY.name(), activityDataSource());
        // 设置默认数据源、其他数据源
        return new DynamicDataSource(masterDataSource(), targetDataSourcesMap);
    }


}

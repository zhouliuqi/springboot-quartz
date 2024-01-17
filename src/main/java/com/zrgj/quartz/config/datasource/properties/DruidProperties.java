package com.zrgj.quartz.config.datasource.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 肘劉祁
 */

@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource.dynamic.druid")
public class DruidProperties {
    /**
     * 用于校验连接是否有效的 SQL 查询语句
     */
    private String validationQuery;

    /**
     * 初始化连接池大小
     */
    private Integer initialSize;


    /**
     * 连接池保持的最小空闲连接数
     */
    private Integer minIdle;

    /**
     * 连接池中同时活动的最大连接数
     */
    private Integer maxActive;

    /**
     * 获取连接时最大等待时间，单位毫秒
     */
    private Integer maxWait;

    /**
     * 连接池中连接在空闲状态下最小的生存时间，超过该时间会被关闭，单位是毫秒
     */
    private Integer minEvictableIdleTimeMillis;

    /**
     * 连接池中连接在空闲状态下最大的生存时间，超过该时间会被关闭，单位是毫秒
     */
    private Integer maxEvictableIdleTimeMillis;

    /**
     * 配置间隔多久进行一次检测，检测需要关闭的空闲连接，单位毫秒。 默认是60s，太长可能会导致无法及时检测到连接中断
     */
    private Integer timeBetweenEvictionRunsMillis;
    /**
     * 是否在获取连接时执行测试查询，用于检测连接是否有效
     */
    private Boolean  testOnBorrow;

    /**
     * 是否在连接空闲时执行测试查询，用于检测连接是否有效
     */
    private Boolean testWhileIdle;

    /**
     * 否在归还连接时执行测试查询，用于检测连接是否有效
     */
    private Boolean testOnReturn;

    /**
     * 配置是否定期探活
     */
    private Boolean keepAlive;

    /**
     * 配置探活间隔 默认120s
     */
    private Integer keepAliveBetweenTimeMillis;

    /**
     * 配置一个连接最大使用次数，避免长时间使用相同连接造成服务器端负载不均衡
     */
    private Integer phyMaxUseCount;


    /**
     * 可以在连接关闭时记录日志
     */
    private Boolean logAbandoned;

    /**
     * 开启缓存 可以提升性能
     */
    private Boolean poolPreparedStatements;

    /**
     * stat 可用于统计监控连接池的活动情况，wall 可用于防止 SQL 注入攻击，slf4j 可用于记录连接池的日志
     */
    private String filters;
}

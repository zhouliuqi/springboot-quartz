package com.zrgj.quartz.config.datasource;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author 肘劉祁
 * 会话配置类
 */
@Configuration
public class SqlSessionConfig {

    /**
     * 注册工厂bean
     * @param dynamicDataSource 动态数据源
     * @return 工厂bean
     * @throws Exception 异常
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource)
            throws Exception {
        //初始化工厂bean
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        //设置数据源
        bean.setDataSource(dynamicDataSource);
        //配置映射文件路径
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:xml/*.xml"));
        //获取bean工厂
        return bean.getObject();
    }

    /**
     * 注册会话模版
     * @param sqlSessionFactory 会话工厂
     * @return 会话模块
     * @throws Exception 异常
     */
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}

package com.zrgj.quartz.config;

import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author 肘劉祁
 * 定时任务配置类
 */
@Configuration
@Component
public class QuartzConfig {


    @Resource
    @Qualifier("quartzDataSource")
    private DataSource quartzDataSource;

    @Bean
    public SchedulerFactoryBean schedulerFactory() {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        //配置调度器的名称
        factory.setSchedulerName("quartz");
        //应用程序启动时自动启动
        factory.setAutoStartup(true);
        //配置调度器启动时的延迟时间
        factory.setStartupDelay(1);
        //应用程序关闭时，是否等待所有调度的Job完成后再关闭Quartz调度器
        factory.setWaitForJobsToCompleteOnShutdown(true);
        //在调度任务时，如果已经存在同名的Job（作业），是否覆盖现有的Job
        factory.setOverwriteExistingJobs(true);
        //配置信息
        factory.setQuartzProperties(properties());
        //设置数据源
        factory.setDataSource(quartzDataSource);
        return factory;
    }

    /**
     * 配置信息
     * @return Properties
     */
    private  Properties properties(){
        Properties properties = new Properties();
        //调度标识名 集群中每一个实例都必须使用相同的名称;ID设置为自动获取 每一个必须不同
        properties.setProperty(StdSchedulerFactory.PROP_SCHED_INSTANCE_NAME,"TestScheduler");
        //集群配置节点实例ID
        properties.setProperty(StdSchedulerFactory.PROP_SCHED_INSTANCE_ID,StdSchedulerFactory.AUTO_GENERATE_INSTANCE_ID);
        //实例化ThreadPool时，使用的线程类为SimpleThreadPool
        properties.setProperty(StdSchedulerFactory.PROP_THREAD_POOL_CLASS,"org.quartz.simpl.SimpleThreadPool");
        //线程总数
        properties.setProperty("org.quartz.threadPool.threadCount","20");
        //线程优先级
        properties.setProperty("org.quartz.threadPool.threadPriority","5");
        //是否在初始化线程的上下文类加载器（Context Class Loader）中创建工作线程
        properties.setProperty(StdSchedulerFactory.PROP_SCHED_SCHEDULER_THREADS_INHERIT_CONTEXT_CLASS_LOADER_OF_INITIALIZING_THREAD,"true");
        //通过事务存储数据库
        properties.setProperty(StdSchedulerFactory.PROP_JOB_STORE_CLASS, "org.springframework.scheduling.quartz.LocalDataSourceJobStore");
        //负责执行特定数据库可能需要的任何JDBC工作
        properties.setProperty("org.quartz.jobStore.driverDelegateClass","org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
        //表前缀
        properties.setProperty("org.quartz.jobStore.tablePrefix","qrtz_");
        //是否集群
        properties.setProperty("org.quartz.jobStore.isClustered","true");
        //调度实例失效的检查时间间隔
        properties.setProperty("org.quartz.jobStore.clusterCheckinInterval","10000");
        //true-Properties文件来存储其配置信息 false-以XML格式保存
        properties.setProperty("org.quartz.jobStore.useProperties","false");
        return properties;
    }

}

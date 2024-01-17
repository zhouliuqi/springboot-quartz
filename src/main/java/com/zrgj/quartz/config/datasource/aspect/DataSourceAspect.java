package com.zrgj.quartz.config.datasource.aspect;

import com.zrgj.quartz.config.datasource.DataSourceType;
import com.zrgj.quartz.config.datasource.DynamicDataSource;
import com.zrgj.quartz.config.datasource.annotion.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author 肘劉祁
 * 数据源切面
 */

@Slf4j
@Aspect
@Order(0)
@Component
public class DataSourceAspect {

    /**
     * 设置切入点
     */
    @Pointcut("@annotation(com.zrgj.quartz.config.datasource.annotion.DataSource)||" +
            "@within(com.zrgj.quartz.config.datasource.annotion.DataSource)")
    public void dataSourcePointCut() {

    }


    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        //初始化数据源注解
        DataSource ds = null;
        //获取执行方法所属类
        Class<?> target = point.getTarget().getClass();
        //获取方法签名
        MethodSignature signature = (MethodSignature) point.getSignature();
        //获取方法
        Method method = signature.getMethod();
        //获取数据源
        ds = resolveDataSource(target, method);
        //判断是否为空
        if (ds == null) {
            //设置默认数据源
            DynamicDataSource.setDataSource(DataSourceType.MASTER.name());
            log.info("set default datasource is " + DataSourceType.MASTER);
        } else {
            //设置指定数据源
            DynamicDataSource.setDataSource(ds.value().name());
            log.info("set datasource is " + ds.value().name());
        }
        try {
            //执行方法
            return point.proceed();
        } finally {
            DynamicDataSource.clearDataSource();
        }
    }


    /**
     * 获取数据源切换的注解
     *
     * @param clazz  类
     * @param method 方法
     * @return 数据源注解
     */
    private DataSource resolveDataSource(Class<?> clazz, Method method) {
        try {
            DataSource ds = null;
            // 判断类上是否有切换数据源注解
            if (clazz.isAnnotationPresent(DataSource.class)) {
                //获取数据源注解
                ds = clazz.getAnnotation(DataSource.class);
            }
            //判断方法上是否有切换数据源注解
            if (method != null && method.isAnnotationPresent(DataSource.class)) {
                ds = method.getAnnotation(DataSource.class);
            }
            return ds;
        } catch (Exception e) {
            log.error(clazz + ":" + e.getMessage());
        }
        return null;
    }
}
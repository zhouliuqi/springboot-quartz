package com.zrgj.quartz;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.zrgj.quartz.constants.ScheduleConstants;
import com.zrgj.quartz.map.SysJobLogMap;
import com.zrgj.quartz.mybatis.pojo.SysJobLog;
import com.zrgj.quartz.mybatis.service.SysJobLogService;
import com.zrgj.quartz.vo.SysJobVo;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 定时任务抽象调用类
 *
 * @author 肘劉祁
 */
@Slf4j
public abstract class AbstractQuartzJob implements Job {

    /**
     * 线程本地变量
     */
    private static final ThreadLocal<Date> THREAD_LOCAL = new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext context) {
        //初始化对象
        SysJobVo job = new SysJobVo();
        //赋值操作
        BeanUtils.copyProperties(context.getMergedJobDataMap().get(ScheduleConstants.TASK_PROPERTIES), job);
        try {
            //前置操作
            before(context, job);
            //执行方法
            doExecute(context, job);
            //后置操作
            after(context, job, null);
        } catch (Exception e) {
            log.error("任务执行异常 - :", e);
            //执行之后
            after(context, job, e);
        }
    }

    /**
     * 执行前
     *
     * @param context 工作执行上下文对象
     * @param sysJob  系统计划任务
     */
    protected void before(JobExecutionContext context, SysJobVo sysJob) {
        //设置开始时间
        THREAD_LOCAL.set(new Date());
    }

    /**
     * 执行后
     *
     * @param context 工作执行上下文对象
     * @param sysJob  系统计划任务
     */
    protected void after(JobExecutionContext context, SysJobVo sysJob, Exception e) {
        //获取开始时间
        Date startTime = THREAD_LOCAL.get();
        //删除线程变量
        THREAD_LOCAL.remove();
        //获取工作日志对象
        SysJobLog sysJobLog =
                SysJobLogMap.INSTANCE.sysJobVoToSysJobLog(sysJob)
                        //开始时间
                        .setStartTime(startTime)
                        //结束时间
                        .setEndTime(new Date());
        //计算消耗时间
        long runMs = sysJobLog.getEndTime().getTime() - sysJobLog.getStartTime().getTime();
        //日志信息
        sysJobLog.setLogInfo(sysJob.getJobName() + " 总共耗时：" + runMs + "毫秒");
        //判断执行结果
        if (e != null) {
            //任务状态
            sysJobLog.setIsSuccess(Constants.NO);
            //异常信息
            sysJobLog.setErrorInfo(ExceptionUtil.stacktraceToString(e,2000));
        } else {
            //任务状态
            sysJobLog.setIsSuccess(Constants.IS);
        }
        //写入数据库当中
        SpringUtil.getBean(SysJobLogService.class).insertJobLog(sysJobLog);
    }

    /**
     * 执行方法，由子类重载
     *
     * @param context 工作执行上下文对象
     * @param sysJob  系统计划任务
     * @throws Exception 执行过程中的异常
     */
    protected abstract void doExecute(JobExecutionContext context, SysJobVo sysJob) throws Exception;
}

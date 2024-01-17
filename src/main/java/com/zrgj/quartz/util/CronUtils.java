package com.zrgj.quartz.util;

import cn.hutool.core.date.DateUtil;
import com.zrgj.quartz.constants.misfire.JobCronMisfireConstants;
import com.zrgj.quartz.vo.SysCronJobVo;
import com.zrgj.quartz.vo.SysJobVo;
import org.apache.commons.lang3.ObjectUtils;
import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * cron表达式工具类
 *
 * @author ruoyi
 */
public class CronUtils {
    /**
     * 返回一个布尔值代表一个给定的Cron表达式的有效性
     *
     * @param cronExpression Cron表达式
     * @return boolean 表达式是否有效
     */
    public static boolean isValid(String cronExpression) {
        return CronExpression.isValidExpression(cronExpression);
    }

    /**
     * 返回下一个执行时间根据给定的Cron表达式
     *
     * @param cronExpression Cron表达式
     * @return Date 下次Cron表达式执行时间
     */
    public static Date getNextExecution(String cronExpression) {
        try {
            //创建时间表达式对象
            CronExpression cron = new CronExpression(cronExpression);
            //判断当前时间是否大于下一次执行时间
            return cron.getNextValidTimeAfter(new Date(System.currentTimeMillis()));
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * 通过表达式获取近10次的执行时间
     * @param cron 表达式
     * @return 时间列表
     */
    public static List<String> getRecentTriggerTime(String cron) {
        //初始化存储容器
        List<String> list = new ArrayList<String>();
        try {
            //创建时间表达式触发器
            CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();
            //设置时间表达式
            cronTriggerImpl.setCronExpression(cron);
            //生成最近10次执行时间
            List<Date> dates = TriggerUtils.computeFireTimes(cronTriggerImpl, null, 10);
            //遍历生成的执行时间列表
            for (Date date : dates) {
                //格式化时间
                list.add(DateUtil.format(date, "yyyy-MM-dd HH:mm:ss"));
            }
        } catch (ParseException e) {
            return null;
        }
        return list;
    }


    /**
     * 设置定时任务策略
     * @param cronJob 定时任务对象
     * @param cb 定时任务构建器
     */
    public static CronScheduleBuilder handleCronScheduleMisfirePolicy(SysCronJobVo cronJob, CronScheduleBuilder cb)
             {
        switch (cronJob.getMisfirePolicy()) {
            case JobCronMisfireConstants.MISFIRE_INSTRUCTION_SMART_POLICY:
                return cb;
            case JobCronMisfireConstants.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY:
                return cb.withMisfireHandlingInstructionIgnoreMisfires();
            case JobCronMisfireConstants.MISFIRE_INSTRUCTION_FIRE_ONCE_NOW:
                return cb.withMisfireHandlingInstructionFireAndProceed();
            case JobCronMisfireConstants.MISFIRE_INSTRUCTION_DO_NOTHING:
                return cb.withMisfireHandlingInstructionDoNothing();
            default:
                throw new RuntimeException("The task misfire policy '" + cronJob.getMisfirePolicy()
                        + "' cannot be used in cron schedule tasks");
        }
    }

    /**
     * 执行操作
     * @param scheduler 调度对象
     * @param jobDetail 工作实例
     * @param job 任务
     * @throws SchedulerException 异常信息
     */
    public  static void scheduleJob(Scheduler scheduler,JobDetail jobDetail, SysJobVo job) throws SchedulerException {
        // 获取工作编号
        Integer jobId = job.getJobId();
        //获取工作组名称
        String jobGroup = job.getJobGroup();
        //转换类型对象
        SysCronJobVo cronJob = job.getCronJob();
        // 表达式调度构建器
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronJob.getCron());
        //数组定时任务策略
        cronScheduleBuilder = CronUtils.handleCronScheduleMisfirePolicy(cronJob, cronScheduleBuilder);
        // 构建一个新的trigger
        CronTrigger trigger =
                TriggerBuilder.newTrigger()
                        //设置触发器键
                        .withIdentity(ScheduleUtils.getTriggerKey(jobId, jobGroup))
                        //开始时间
                        .startAt(job.getStartTime())
                        //结束
                        .endAt(job.getEndTime())
                .withSchedule(cronScheduleBuilder)
                        .build();
        //获取触发器
        trigger = (CronTrigger) ScheduleUtils.getTrigger(scheduler,
                jobDetail,
                job,
                job.getStartTime(),
                trigger);
        // 判断任务是否过期
        if (ObjectUtils.isNotEmpty(CronUtils.getNextExecution(cronJob.getCron())))
        {
            // 执行调度任务
            scheduler.scheduleJob(jobDetail, trigger);
        }
    }

}

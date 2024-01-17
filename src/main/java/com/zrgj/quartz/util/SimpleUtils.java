package com.zrgj.quartz.util;

import com.zrgj.quartz.constants.misfire.JobSimpleMisfireConstants;
import com.zrgj.quartz.constants.unit.JobSimpleUnitConstants;
import com.zrgj.quartz.vo.SysJobVo;
import com.zrgj.quartz.vo.SysSimpleJobVo;
import org.quartz.*;

/**
 * @author 肘劉祁
 * 普通任务工具类
 */

public class SimpleUtils {


    /**
     * 执行操作
     *
     * @param scheduler 调度对象
     * @param jobDetail 工作实例
     * @param job       任务
     */
    public static void scheduleJob(Scheduler scheduler, JobDetail jobDetail, SysJobVo job) throws SchedulerException {
        // 获取工作编号
        Integer jobId = job.getJobId();
        //获取工作组名称
        String jobGroup = job.getJobGroup();
        //转换类型对象
        SysSimpleJobVo simpleJob = job.getSimpleJob();
        //构建一个新的trigger
        SimpleTrigger trigger =
                TriggerBuilder.newTrigger()
                        .withIdentity(ScheduleUtils.getTriggerKey(jobId, jobGroup))
                        //开始时间
                        .startAt(job.getStartTime())
                        //结束
                        .endAt(job.getEndTime())
                        .withSchedule(handleSimpleScheduleMisfirePolicy(simpleJob))
                        .build();
        //获取触发器
        trigger = (SimpleTrigger) ScheduleUtils.getTrigger(scheduler, jobDetail, job, job.getStartTime(), trigger);
        // 执行调度任务
        scheduler.scheduleJob(jobDetail, trigger);
    }


    /**
     * 设置定时任务策略
     *
     * @param simpleJob 普通任务对象
     */
    public static SimpleScheduleBuilder handleSimpleScheduleMisfirePolicy(SysSimpleJobVo simpleJob) {
        //初始化对象
        SimpleScheduleBuilder simpleSchedule = SimpleScheduleBuilder.simpleSchedule();
        //判断策略
        switch (simpleJob.getMisfirePolicy()) {
            case JobSimpleMisfireConstants.MISFIRE_INSTRUCTION_SMART_POLICY:
                break;
            case JobSimpleMisfireConstants.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY:
                simpleSchedule.withMisfireHandlingInstructionIgnoreMisfires();
                break;
            case JobSimpleMisfireConstants.MISFIRE_INSTRUCTION_FIRE_NOW:
                simpleSchedule.withMisfireHandlingInstructionFireNow();
                break;
            case JobSimpleMisfireConstants.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT:
                simpleSchedule.withMisfireHandlingInstructionNextWithExistingCount();
                break;
            case JobSimpleMisfireConstants.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT:
                simpleSchedule.withMisfireHandlingInstructionNextWithRemainingCount();
                break;
            case JobSimpleMisfireConstants.MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT:
                simpleSchedule.withMisfireHandlingInstructionNowWithExistingCount();
                break;
            case JobSimpleMisfireConstants.MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT:
                simpleSchedule.withMisfireHandlingInstructionNowWithRemainingCount();
                break;
            default:
                throw new RuntimeException("The task misfire policy '" + simpleJob.getMisfirePolicy()
                        + "' cannot be used in simple schedule tasks");
        }
        //设置执行次数
        simpleSchedule.withRepeatCount(simpleJob.getCount());
        //匹配时间单位
        switch (simpleJob.getTimeUnit()) {
            case JobSimpleUnitConstants.MILLISECOND:
                simpleSchedule.withIntervalInMilliseconds(simpleJob.getTimeInterval());
                break;
            case JobSimpleUnitConstants.SECOND:
                simpleSchedule.withIntervalInSeconds(simpleJob.getTimeInterval());
                break;
            case JobSimpleUnitConstants.MINUTE:
                simpleSchedule.withIntervalInMinutes(simpleJob.getTimeInterval());
                break;
                case JobSimpleUnitConstants.HOUR:
                    simpleSchedule.withIntervalInHours(simpleJob.getTimeInterval());
                break;
            default:
                throw new RuntimeException(simpleJob.getTimeUnit()+"as invalid time unit");
        }
        return simpleSchedule;
    }

}

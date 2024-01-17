package com.zrgj.quartz.util;

import cn.hutool.extra.spring.SpringUtil;
import com.zrgj.quartz.Constants;
import com.zrgj.quartz.constants.JobTypeConstants;
import com.zrgj.quartz.constants.ScheduleConstants;
import com.zrgj.quartz.execution.QuartzDisallowConcurrentExecution;
import com.zrgj.quartz.execution.QuartzJobExecution;
import com.zrgj.quartz.vo.SysJobVo;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;

import java.util.Date;

/**
 * 定时任务工具类
 *
 * @author 肘劉祁
 */
public class ScheduleUtils {
    /**
     * 得到quartz任务类
     *
     * @param sysJob 执行计划
     * @return 具体执行任务类
     */
    private static Class<? extends Job> getQuartzJobClass(SysJobVo sysJob) {
        //是否允许并发
        boolean isConcurrent = Constants.IS.equals(sysJob.getIsCurrent());
        //判断执行类
        return isConcurrent ? QuartzJobExecution.class : QuartzDisallowConcurrentExecution.class;
    }

    /**
     * 构建任务触发对象
     * @param jobId 任务编号
     * @param jobGroup 任务组名称
     */
    public static TriggerKey getTriggerKey(Integer jobId, String jobGroup) {
        return TriggerKey.triggerKey(ScheduleConstants.TASK_CLASS_NAME + jobId, jobGroup);
    }

    /**
     * 构建任务键对象
     * @param jobId 任务编号
     * @param jobGroup 任务组名称
     */
    public static JobKey getJobKey(Integer jobId, String jobGroup) {
        return JobKey.jobKey(ScheduleConstants.TASK_CLASS_NAME + jobId, jobGroup);
    }

    /**
     * 创建定时任务
     * @param scheduler 调度器
     * @param job 任务
     */
    public static void createScheduleJob(Scheduler scheduler, SysJobVo job) throws SchedulerException {
        //获取操作执行类
        Class<? extends Job> jobClass = getQuartzJobClass(job);
        // 获取工作编号
        Integer jobId = job.getJobId();
        //获取工作组名称
        String jobGroup = job.getJobGroup();
        //创建工作实例
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(getJobKey(jobId, jobGroup)).build();
        //判断工作类型
        switch (job.getType()) {
            case JobTypeConstants.SIMPLE:
                //创建任务
                SimpleUtils.scheduleJob(scheduler, jobDetail, job);
                break;
            case JobTypeConstants.CRON:
                //创建任务
                CronUtils.scheduleJob(scheduler, jobDetail, job);
                break;
            default:
                throw new JobExecutionException("No job type " + job.getType() + " found");
        }
        // 暂停任务
        if (Constants.IS.equals(job.getIsPause())) {
            scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
    }


    /**
     * 检查包名是否为白名单配置
     *
     * @param invokeTarget 目标字符串
     * @return 结果
     */
    public static boolean whiteList(String invokeTarget) {
        //获取在指定字符串之前的子字符串
        String packageName = StringUtils.substringBefore(invokeTarget, "(");
        //计算一个字符串中特定子字符串的出现次数
        int count = StringUtils.countMatches(packageName, ".");
        //判断是否大于1
        if (count > 1) {
            //检查字符串是否包含一组指定的子字符串，而且不区分大小写
            return StringUtils.containsAnyIgnoreCase(invokeTarget, ScheduleConstants.JOB_WHITELIST_STR);
        }
        //字符串切割获取第一个元素
        Object obj = SpringUtil.getBean(StringUtils.split(invokeTarget, ".")[0]);
        //获取对象的包名
        String beanPackageName = obj.getClass().getPackage().getName();
        //检查字符串是否包含一组指定的子字符串，而且不区分大小写 并且 不能包含违规的字符
        return StringUtils.containsAnyIgnoreCase(beanPackageName, ScheduleConstants.JOB_WHITELIST_STR)
                && !StringUtils.containsAnyIgnoreCase(beanPackageName, ScheduleConstants.JOB_ERROR_STR);
    }

    /**
     * 获取触发器
     * @param scheduler 调度器
     * @param jobDetail 工作实例
     * @param job 任务
     * @param startDate 开始时间
     * @param trigger 触发器
     * @return 触发器
     * @throws SchedulerException 异常信息
     */
    public static Trigger getTrigger(Scheduler scheduler,
                                     JobDetail jobDetail,
                                     SysJobVo job,
                                     Date startDate,
                                     Trigger trigger
    ) throws SchedulerException {
        //判断开始时间是否为空
        if (startDate==null){
            trigger= trigger.getTriggerBuilder().startNow().build();
        }
        // 放入参数，运行时的方法可以获取
        jobDetail.getJobDataMap().put(ScheduleConstants.TASK_PROPERTIES, job);
        //生成工作键
        JobKey jobKey = ScheduleUtils.getJobKey(job.getJobId(), job.getJobGroup());
        // 判断是否存在
        if (scheduler.checkExists(jobKey)) {
            // 防止创建时存在数据问题 先移除，然后在执行创建操作
            scheduler.deleteJob(jobKey);
        }
        return trigger;
    }
}
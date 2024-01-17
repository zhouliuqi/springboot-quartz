package com.zrgj.quartz.function.service.impl;

import com.zrgj.quartz.Constants;
import com.zrgj.quartz.constants.JobTypeConstants;
import com.zrgj.quartz.constants.ScheduleConstants;
import com.zrgj.quartz.function.service.JobFunctionService;
import com.zrgj.quartz.map.SysJobMap;
import com.zrgj.quartz.mybatis.pojo.SysJob;
import com.zrgj.quartz.mybatis.service.SysCronJobService;
import com.zrgj.quartz.mybatis.service.SysJobService;
import com.zrgj.quartz.mybatis.service.SysSimpleJobService;
import com.zrgj.quartz.util.CronUtils;
import com.zrgj.quartz.util.ScheduleUtils;
import com.zrgj.quartz.vo.SysCronJobVo;
import com.zrgj.quartz.vo.SysJobVo;
import com.zrgj.quartz.vo.SysSimpleJobVo;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 肘劉祁
 * 任务功能实现类
 */
@Service
@Slf4j
public class JobFunctionServiceImpl implements JobFunctionService {

    @Resource
    private SysJobService jobService;

    @Resource
    private SysCronJobService cronJobService;

    @Resource
    private SysSimpleJobService simpleJobService;

    @Resource
    private Scheduler scheduler;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean pauseJob(String uuid) {
        //获取任务信息
        SysJobVo jobVo = jobService.getJobBasicInfoByUuid(uuid);
        //判断是否存在
        if (jobVo == null) {
            return false;
        }
        //修改工作任务
        if (jobService.myUpdateById(new SysJob()
                .setId(jobVo.getJobId())
                .setIsPause(Constants.IS))) {
            try {
                scheduler.pauseJob(ScheduleUtils.getJobKey(jobVo.getJobId(), jobVo.getJobGroup()));
            } catch (SchedulerException e) {
                log.error(e.getMessage());
                throw new  RuntimeException(e);
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean resumeJob(String uuid) {
        //获取任务信息
        SysJobVo jobVo = jobService.getJobBasicInfoByUuid(uuid);
        //判断是否存在
        if (jobVo == null) {
            return false;
        }
        //修改工作任务
        if (jobService.myUpdateById(new SysJob()
                .setId(jobVo.getJobId())
                .setIsPause(Constants.NO))) {
            try {
                scheduler.resumeJob(ScheduleUtils.getJobKey(jobVo.getJobId(), jobVo.getJobGroup()));
            } catch (SchedulerException e) {
                log.error(e.getMessage());
                throw new  RuntimeException(e);
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertJob(SysJobVo sysJob) {
        //获取对象
        SysJob job = SysJobMap.INSTANCE.sysJobVoToSysJob(sysJob);
        //判断是否添加成功
        if (!jobService.mySave(job)) {
            return false;
        }
        //编号赋值
        sysJob.setJobId(job.getId());
        //判断类型
        switch (sysJob.getType()) {
            case JobTypeConstants.SIMPLE:
                //转换类型
                SysSimpleJobVo simpleJob = sysJob.getSimpleJob();
                //添加任务
                simpleJobService.mySave(simpleJob, sysJob.getJobId());
                break;
            case JobTypeConstants.CRON:
                //转换类型
                SysCronJobVo cronJob = sysJob.getCronJob();
                //添加任务
                cronJobService.mySave(cronJob, sysJob.getJobId());
                break;
            default:
                //其他类型
                return false;
        }
        try {
            //添加任务
            ScheduleUtils.createScheduleJob(scheduler, sysJob);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new  RuntimeException(e);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateJob(SysJobVo sysJob) {
        //获取对象
        SysJob job = SysJobMap.INSTANCE.sysJobVoToSysJob(sysJob);
        //判断是否修改成功
        if (!jobService.myUpdateById(job)) {
            return false;
        }
        //判断类型
        switch (sysJob.getType()) {
            case JobTypeConstants.SIMPLE:
                //转换类型
                SysSimpleJobVo simpleJob = sysJob.getSimpleJob();
                //添加任务
                simpleJobService.myUpdate(simpleJob, sysJob.getJobId());
                break;
            case JobTypeConstants.CRON:
                //转换类型
                SysCronJobVo cronJob = sysJob.getCronJob();
                //添加任务
                cronJobService.myUpdate(cronJob, sysJob.getJobId());
                break;
            default:
                //其他类型
                return false;
        }
        try {
            //获取键
            JobKey jobKey = ScheduleUtils.getJobKey(sysJob.getJobId(), sysJob.getJobGroup());
            //判断键是否存在
            if (scheduler.checkExists(jobKey)) {
                // 防止创建时存在数据问题 先移除，然后在执行创建操作
                scheduler.deleteJob(jobKey);
            }
            //添加任务
            ScheduleUtils.createScheduleJob(scheduler, sysJob);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new  RuntimeException(e);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteJob(String uuid) {
        //获取任务信息
        SysJobVo jobVo = jobService.getJobBasicInfoByUuid(uuid);
        //判断是否存在
        if (jobVo == null) {
            return false;
        }
        //修改工作任务
        if (jobService.myRemoveById(jobVo.getJobId())) {
            try {
                scheduler.deleteJob(ScheduleUtils.getJobKey(jobVo.getJobId(), jobVo.getJobGroup()));
            } catch (SchedulerException e) {
                log.error(e.getMessage());
                throw new  RuntimeException(e);
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteJobByUuids(List<String> uuids) {
        //遍历唯一标识
        for (String uuid : uuids) {
            //删除任务
            if (!deleteJob(uuid)) {
                return false;
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean run(String uuid) {
        //获取任务信息
        SysJobVo jobVo = jobService.getJobDetailsByUuId(uuid);
        //判断是否存在
        if (jobVo == null) {
            return false;
        }
        //获取工作键
        JobKey jobKey = ScheduleUtils.getJobKey(jobVo.getJobId(), jobVo.getJobGroup());
        try {
            //判断键是否存在
            if (scheduler.checkExists(jobKey)) {
                //初始化参数对象
                JobDataMap dataMap = new JobDataMap();
                //添加参数
                dataMap.put(ScheduleConstants.TASK_PROPERTIES, jobVo);
                //立即执行
                scheduler.triggerJob(jobKey, dataMap);
            }
        } catch (SchedulerException e) {
            log.error(e.getMessage());
            throw new  RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean checkCronExpressionIsValid(String icon) {
        return CronUtils.isValid(icon);
    }

    @Override
    public SysJobVo getJobDetailsByUuId(String uuid) {
        return jobService.getJobDetailsByUuId(uuid);
    }

    @Override
    public List<SysJobVo> getJobBasicInfoList() {
        return jobService.getJobBasicInfoList();
    }
}

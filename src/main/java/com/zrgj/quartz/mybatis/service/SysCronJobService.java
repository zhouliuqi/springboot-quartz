package com.zrgj.quartz.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrgj.quartz.mybatis.pojo.SysCronJob;
import com.zrgj.quartz.vo.SysCronJobVo;

/**
 * <p>
 * 时间表达式任务表 服务类
 * </p>
 *
 * @author 周刘奇
 * @since 2023-10-11
 */
public interface SysCronJobService extends IService<SysCronJob> {


    /**
     * 添加时间表达式任务
     * @param jobId 任务编号
     * @param cronJob 时间表达式任务
     * @return 操作结果
     */
    boolean mySave(SysCronJobVo cronJob,Integer jobId);

    /**
     * 更新时间表达式任务
     * @param jobId 任务编号
     * @param cronJob 时间表达式任务
     * @return 操作结果
     */
    boolean myUpdate(SysCronJobVo cronJob,Integer jobId);
}

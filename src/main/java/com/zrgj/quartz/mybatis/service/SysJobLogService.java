package com.zrgj.quartz.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrgj.quartz.mybatis.pojo.SysJobLog;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 任务日志表 服务类
 * </p>
 *
 * @author 周刘奇
 * @since 2023-10-11
 */
public interface SysJobLogService extends IService<SysJobLog> {


    /**
     * 物理删除所有的日志
     *
     * @return 操作结果
     */
    boolean cleanJobLog();

    /**
     * 逻辑删除所有的日志
     *
     * @return 操作结果
     */
    boolean logicCleanJobLog();

    /**
     * 物理批量删除日志
     * @param ids 编号列表
     * @return 操作结果
     */
    boolean cleanBathJobLog(List<Integer> ids);

    /**
     * 逻辑批量删除日志
     * @param ids 编号列表
     * @return 操作结果
     */
    boolean logicCleanBathJobLog(List<Integer> ids);


    /**
     * 物理删除日志
     * @param jobId 工作编号
     * @return 操作结果
     */
    boolean cleanJobLogByJobId(Integer jobId);

    /**
     * 逻辑删除日志
     * @param jobId 工作编号
     * @return 操作结果
     */
    boolean logicCleanJobLogByJobId(Integer jobId);

    /**
     * 物理删除日志
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 操作结果
     */
    boolean cleanJobLogByTime(Date startTime,Date endTime);

    /**
     * 逻辑删除日志
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 操作结果
     */
    boolean logicCleanJobLogByTime(Date startTime,Date endTime);

    /**
     * 新增日志
     * @param jobLog 日志对象
     */
    void insertJobLog(SysJobLog jobLog);
}

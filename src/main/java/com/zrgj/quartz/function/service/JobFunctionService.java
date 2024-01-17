package com.zrgj.quartz.function.service;

import com.zrgj.quartz.vo.SysJobVo;

import java.util.List;

/**
 * @author 肘劉祁
 * 任务功能接口
 */

public interface JobFunctionService {

    /**
     * 暂停任务
     * @param uuid 任务唯一标识
     * @return 操作结果
     */
    boolean pauseJob(String uuid);

    /**
     * 恢复任务
     * @param uuid 任务唯一标识
     * @return 操作结果
     */
    boolean resumeJob(String uuid);

    /**
     * 添加任务
     * @param sysJob 任务信息
     * @return 操作结果
     */
    boolean insertJob(SysJobVo sysJob);


    /**
     * 更新任务
     * @param sysJob 任务对象
     * @return 操作结果
     */
    boolean updateJob(SysJobVo sysJob);

    /**
     * 删除任务
     * @param uuid 唯一标识
     * @return 操作结果
     */
    boolean deleteJob(String uuid);

    /**
     * 批量删除任务
     * @param uuids 唯一标识集合
     * @return 操作结果
     */
    boolean deleteJobByUuids(List<String> uuids);


    /**
     * 立即运行
     * @param uuid 唯一标识
     * @return 操作结果
     */
    boolean run(String uuid);

    /**
     * 校验时间表达式是否有效
     * @param icon 时间表达式
     * @return 验证结果
     */
    boolean checkCronExpressionIsValid(String icon);

    /**
     * 获取任务详情
     * @param uuid 唯一标识
     * @return 任务对象
     */
    SysJobVo getJobDetailsByUuId(String uuid);


    /**
     * 获取任务基本信息列表
     * @return 列表数据
     */
    List<SysJobVo> getJobBasicInfoList();

}

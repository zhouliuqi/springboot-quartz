package com.zrgj.quartz.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrgj.quartz.mybatis.pojo.SysJob;
import com.zrgj.quartz.vo.SysJobVo;

import java.util.List;

/**
 * <p>
 * 任务表 服务类
 * </p>
 *
 * @author 周刘奇
 * @since 2023-10-11
 */
public interface SysJobService extends IService<SysJob> {


    /**
     * 添加任务
     * @param job 任务信息
     * @return 操作结果
     */
    boolean mySave(SysJob job);


    /**
     * 更新任务
     * @param job 任务对象
     * @return 操作结果
     */
    boolean myUpdateById(SysJob job);



    /**
     * 获取任务详情
     * @param uuid 任务唯一标识
     * @return 任务对象
     */
    SysJobVo getJobDetailsByUuId(String uuid);


    /**
     * 获取任务基本信息
     * @param uuid 任务唯一标识
     * @return 任务对象
     */
    SysJobVo getJobBasicInfoByUuid(String uuid);


    /**
     * 删除任务
     * @param id 任务编号
     * @return 操作结果
     */
    boolean myRemoveById(Integer id);

    /**
     * 获取任务基本信息列表
     * @return 列表数据
     */
    List<SysJobVo> getJobBasicInfoList();

}

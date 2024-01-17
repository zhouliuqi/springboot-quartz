package com.zrgj.quartz.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrgj.quartz.mybatis.pojo.SysSimpleJob;
import com.zrgj.quartz.vo.SysSimpleJobVo;

/**
 * <p>
 * 普通任务表 服务类
 * </p>
 *
 * @author 周刘奇
 * @since 2023-10-11
 */
public interface SysSimpleJobService extends IService<SysSimpleJob> {

    /**
     * 添加普通任务'
     * @param jobId  任务编号
     * @param simpleJob 普通任务
     * @return 操作结果
     */
    boolean mySave(SysSimpleJobVo simpleJob,Integer jobId);

    /**
     * 更新普通任务
     * @param jobId 任务编号
     * @param simpleJob 普通任务
     * @return 操作结果
     */
    boolean myUpdate(SysSimpleJobVo simpleJob,Integer jobId);
}

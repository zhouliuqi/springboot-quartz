package com.zrgj.quartz.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrgj.quartz.mybatis.pojo.SysSimpleJob;
import com.zrgj.quartz.vo.SysSimpleJobVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 普通任务表 Mapper 接口
 * </p>
 *
 * @author 周刘奇
 * @since 2023-10-11
 */
public interface SysSimpleJobMapper extends BaseMapper<SysSimpleJob> {

    /**
     * 获取普通任务
     * @param jobId 任务编号
     * @return 任务对象
     */
    SysSimpleJobVo getSimpleJobByJobIdMapper(@Param("jobId") Integer jobId);
}

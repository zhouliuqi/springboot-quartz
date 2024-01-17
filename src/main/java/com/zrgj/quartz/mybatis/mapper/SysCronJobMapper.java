package com.zrgj.quartz.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrgj.quartz.mybatis.pojo.SysCronJob;
import com.zrgj.quartz.vo.SysCronJobVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 时间表达式任务表 Mapper 接口
 * </p>
 *
 * @author 周刘奇
 * @since 2023-10-11
 */
public interface SysCronJobMapper extends BaseMapper<SysCronJob> {

    /**
     * 获取时间表达式任务
     * @param jobId 任务编号
     * @return 任务对象
     */
    SysCronJobVo getCronJobByJobIdMapper(@Param("jobId") Integer jobId);
}

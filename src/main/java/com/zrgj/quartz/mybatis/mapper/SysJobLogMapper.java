package com.zrgj.quartz.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrgj.quartz.mybatis.pojo.SysJobLog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 任务日志表 Mapper 接口
 * </p>
 *
 * @author 周刘奇
 * @since 2023-10-11
 */
public interface SysJobLogMapper extends BaseMapper<SysJobLog> {
    /**
     * 物理删除所有的日志
     * @return  操作结果
     */
    boolean cleanJobLogMapper();

    /**
     * 物理批量删除日志
     * @param ids 编号列表
     * @return 操作结果
     */
    boolean cleanBathJobLogMapper(@Param("ids") List<Integer> ids);

    /**
     * 物理删除日志
     * @param jobId 工作编号
     * @return 操作结果
     */
    boolean cleanJobLogByJobIdMapper(@Param("jobId") Integer jobId);

    /**
     * 物理删除日志
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 操作结果
     */
    boolean cleanJobLogByTimeMapper(@Param("startTime") Date startTime,@Param("endTime") Date endTime);
}

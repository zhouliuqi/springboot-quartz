package com.zrgj.quartz.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrgj.quartz.mybatis.pojo.SysJob;
import com.zrgj.quartz.vo.SysJobVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 任务表 Mapper 接口
 * </p>
 *
 * @author 周刘奇
 * @since 2023-10-11
 */
public interface SysJobMapper extends BaseMapper<SysJob> {


    /**
     * 获取工作任务详情
     * @param uuid 任务唯一标识
     * @return 工作任务对象
     */
    SysJobVo getJobDetailsByUuIdMapper(@Param("uuid") String uuid);

    /**
     * 获取任务基本信息
     * @param uuid 唯一标识
     * @return 任务对象
     */
    SysJobVo getJobBasicInfoByUuidMapper(@Param("uuid") String uuid);

    /**
     * 获取任务基本信息列表
     * @return 列表数据
     */
    List<SysJobVo> getJobBasicInfoListMapper();
}

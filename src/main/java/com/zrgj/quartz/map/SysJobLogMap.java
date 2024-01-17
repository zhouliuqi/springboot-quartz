package com.zrgj.quartz.map;

import com.zrgj.quartz.mybatis.pojo.SysJobLog;
import com.zrgj.quartz.vo.SysJobVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

/**
 * @author 肘劉祁
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysJobLogMap {

    /**
     * 使用工厂获取对象实例
     */
    SysJobLogMap INSTANCE = Mappers.getMapper(SysJobLogMap.class);




    @Mapping(target = "jobName")
    @Mapping(target = "jobGroup")
    SysJobLog sysJobVoToSysJobLog(SysJobVo jobVo);



}

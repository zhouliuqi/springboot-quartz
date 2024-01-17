package com.zrgj.quartz.map;

import cn.hutool.core.util.IdUtil;
import com.zrgj.quartz.mybatis.pojo.SysJob;
import com.zrgj.quartz.vo.SysJobVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

/**
 * @author 肘劉祁
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysJobMap {

    /**
     * 使用工厂获取对象实例
     */
    SysJobMap INSTANCE = Mappers.getMapper(SysJobMap.class);


    @Mapping(target = "id", source = "jobId")
    @Mapping(target = "jobName")
    @Mapping(target = "jobGroup")
    @Mapping(target = "type")
    @Mapping(target = "invokeTarget")
    @Mapping(target = "isCurrent")
    @Mapping(target = "isPause")
    @Mapping(target = "startTime")
    @Mapping(target = "endTime")
    @Mapping(target = "uuid",defaultExpression = "java(cn.hutool.core.util.IdUtil.fastSimpleUUID())")
    SysJob sysJobVoToSysJob(SysJobVo jobVo);



}

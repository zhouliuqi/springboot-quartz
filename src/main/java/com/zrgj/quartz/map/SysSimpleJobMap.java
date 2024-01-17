package com.zrgj.quartz.map;

import com.zrgj.quartz.mybatis.pojo.SysSimpleJob;
import com.zrgj.quartz.vo.SysSimpleJobVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

/**
 * @author 肘劉祁
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysSimpleJobMap {

    /**
     * 使用工厂获取对象实例
     */
    SysSimpleJobMap INSTANCE = Mappers.getMapper(SysSimpleJobMap.class);


    @Mapping(target = "id", source = "simpleJobVo.simpleJobId")
    @Mapping(target = "count")
    @Mapping(target = "timeUnit")
    @Mapping(target = "misfirePolicy")
    @Mapping(target = "jobId", source = "jobId")
    @Mapping(target = "timeInterval")
    SysSimpleJob sysSimpleJobVoToSysSimpleJob(SysSimpleJobVo simpleJobVo, Integer jobId);
}

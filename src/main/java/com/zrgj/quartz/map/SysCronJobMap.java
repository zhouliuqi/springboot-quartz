package com.zrgj.quartz.map;

import com.zrgj.quartz.mybatis.pojo.SysCronJob;
import com.zrgj.quartz.vo.SysCronJobVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

/**
 * @author 肘劉祁
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysCronJobMap {

    /**
     * 使用工厂获取对象实例
     */
    SysCronJobMap INSTANCE = Mappers.getMapper(SysCronJobMap.class);

    @Mapping(target = "id", source = "cronJobVo.cronJobId")
    @Mapping(target = "cron")
    @Mapping(target = "misfirePolicy")
    @Mapping(target = "jobId",source = "jobId")
    SysCronJob sysCronJobVoToSysCronJob(SysCronJobVo cronJobVo,Integer jobId);
}

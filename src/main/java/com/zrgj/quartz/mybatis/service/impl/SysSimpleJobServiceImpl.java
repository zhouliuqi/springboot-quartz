package com.zrgj.quartz.mybatis.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zrgj.quartz.config.datasource.DataSourceType;
import com.zrgj.quartz.config.datasource.annotion.DataSource;
import com.zrgj.quartz.map.SysSimpleJobMap;
import com.zrgj.quartz.mybatis.mapper.SysSimpleJobMapper;
import com.zrgj.quartz.mybatis.pojo.SysSimpleJob;
import com.zrgj.quartz.mybatis.service.SysSimpleJobService;
import com.zrgj.quartz.vo.SysSimpleJobVo;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 普通任务表 服务实现类
 * </p>
 *
 * @author 周刘奇
 * @since 2023-10-11
 */
@Service
@DataSource(DataSourceType.QUARTZ)
public class SysSimpleJobServiceImpl extends ServiceImpl<SysSimpleJobMapper, SysSimpleJob> implements SysSimpleJobService {

    @Override
    public boolean mySave(SysSimpleJobVo simpleJob,Integer jobId) {
       return   this.save(SysSimpleJobMap.INSTANCE.sysSimpleJobVoToSysSimpleJob(simpleJob,jobId));
    }

    @Override
    public boolean myUpdate(SysSimpleJobVo simpleJob,Integer jobId) {
       return this.update(SysSimpleJobMap.INSTANCE.sysSimpleJobVoToSysSimpleJob(simpleJob,jobId),
                new LambdaUpdateWrapper<SysSimpleJob>()
                        .eq(SysSimpleJob::getJobId,jobId)
        );
    }
}

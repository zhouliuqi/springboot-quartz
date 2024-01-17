package com.zrgj.quartz.mybatis.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zrgj.quartz.config.datasource.DataSourceType;
import com.zrgj.quartz.config.datasource.annotion.DataSource;
import com.zrgj.quartz.map.SysCronJobMap;
import com.zrgj.quartz.mybatis.mapper.SysCronJobMapper;
import com.zrgj.quartz.mybatis.pojo.SysCronJob;
import com.zrgj.quartz.mybatis.service.SysCronJobService;
import com.zrgj.quartz.vo.SysCronJobVo;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 时间表达式任务表 服务实现类
 * </p>
 *
 * @author 周刘奇
 * @since 2023-10-11
 */
@Service
@DataSource(DataSourceType.QUARTZ)
public class SysCronJobServiceImpl extends ServiceImpl<SysCronJobMapper, SysCronJob> implements SysCronJobService {

    @Override
    public boolean mySave(SysCronJobVo cronJob,Integer jobId) {
      return   this.save(SysCronJobMap.INSTANCE.sysCronJobVoToSysCronJob(cronJob,jobId));
    }

    @Override
    public boolean myUpdate(SysCronJobVo cronJob,Integer jobId) {
        return update(SysCronJobMap.INSTANCE.sysCronJobVoToSysCronJob(cronJob,jobId),
                new LambdaUpdateWrapper<SysCronJob>().eq(SysCronJob::getJobId, jobId)
        );
    }
}

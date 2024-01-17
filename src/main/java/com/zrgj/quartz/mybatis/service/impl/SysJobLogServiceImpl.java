package com.zrgj.quartz.mybatis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zrgj.quartz.config.datasource.DataSourceType;
import com.zrgj.quartz.config.datasource.annotion.DataSource;
import com.zrgj.quartz.mybatis.mapper.SysJobLogMapper;
import com.zrgj.quartz.mybatis.pojo.SysCronJob;
import com.zrgj.quartz.mybatis.pojo.SysJobLog;
import com.zrgj.quartz.mybatis.service.SysJobLogService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 任务日志表 服务实现类
 * </p>
 *
 * @author 周刘奇
 * @since 2023-10-11
 */
@Service
@DataSource(DataSourceType.QUARTZ)
public class SysJobLogServiceImpl extends ServiceImpl<SysJobLogMapper, SysJobLog> implements SysJobLogService {

    @Override
    public boolean cleanJobLog() {
        return baseMapper.cleanJobLogMapper();
    }

    @Override
    public boolean logicCleanJobLog() {
        System.out.println("11111111111111111111111");
        return  true;
    }


    public  void one(SysCronJob cronJob){
        System.out.println(cronJob);
    }

    public  void two(List<Integer> list){
        System.out.println(list);
    }

    public  void three(String o){
        System.out.println("oooooooooooooooooooooooooooooooooooooooooooooo");
    }

    @Override
    public boolean cleanBathJobLog(List<Integer> ids) {
        return baseMapper.cleanBathJobLogMapper(ids);
    }

    @Override
    public boolean logicCleanBathJobLog(List<Integer> ids) {
        return this.removeByIds(ids);
    }

    @Override
    public boolean cleanJobLogByJobId(Integer jobId) {
        return baseMapper.cleanJobLogByJobIdMapper(jobId);
    }

    @Override
    public boolean logicCleanJobLogByJobId(Integer jobId) {
        return this.remove(new LambdaQueryWrapper<SysJobLog>()
                //匹配工作编号
                .eq(SysJobLog::getId, jobId));
    }

    @Override
    public boolean cleanJobLogByTime(Date startTime, Date endTime) {
        return baseMapper.cleanJobLogByTimeMapper(startTime, endTime);
    }

    @Override
    public boolean logicCleanJobLogByTime(Date startTime, Date endTime) {
        return this.remove(new LambdaQueryWrapper<SysJobLog>()
                //开始时间区间
                .between(SysJobLog::getStartTime, startTime, endTime));
    }

    @Override
    public void insertJobLog(SysJobLog jobLog) {
         this.save(jobLog);
    }
}

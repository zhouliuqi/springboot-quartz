package com.zrgj.quartz.mybatis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zrgj.quartz.config.datasource.DataSourceType;
import com.zrgj.quartz.config.datasource.annotion.DataSource;
import com.zrgj.quartz.mybatis.mapper.SysJobMapper;
import com.zrgj.quartz.mybatis.pojo.SysJob;
import com.zrgj.quartz.mybatis.service.SysJobService;
import com.zrgj.quartz.vo.SysJobVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 任务表 服务实现类
 * </p>
 *
 * @author 周刘奇
 * @since 2023-10-11
 */
@Service
@DataSource(DataSourceType.QUARTZ)
public class SysJobServiceImpl extends ServiceImpl<SysJobMapper, SysJob> implements SysJobService {


    @Override
    public boolean mySave(SysJob job) {
        return this.save(job);
    }

    @Override
    public boolean myUpdateById(SysJob job) {
        return this.updateById(job);
    }

    @Override
    public SysJobVo getJobDetailsByUuId(String uuid) {
        return baseMapper.getJobDetailsByUuIdMapper(uuid);
    }

    @Override
    public SysJobVo getJobBasicInfoByUuid(String uuid) {
        return baseMapper.getJobBasicInfoByUuidMapper(uuid);
    }

    @Override
    public boolean myRemoveById(Integer id) {
        return this.removeById(id);
    }

    @Override
    public List<SysJobVo> getJobBasicInfoList() {
        return baseMapper.getJobBasicInfoListMapper();
    }
}

package com.zrgj.quartz.execution;

import com.zrgj.quartz.AbstractQuartzJob;
import com.zrgj.quartz.mybatis.pojo.SysJob;
import com.zrgj.quartz.util.JobInvokeUtil;
import com.zrgj.quartz.vo.SysJobVo;
import org.quartz.JobExecutionContext;

/**
 * 定时任务处理类 -允许并发执行
 * @author 肘劉祁
 */

public class QuartzJobExecution extends AbstractQuartzJob {

    @Override
    protected void doExecute(JobExecutionContext context, SysJobVo sysJob) throws Exception {
        JobInvokeUtil.invokeMethod(sysJob);
    }
}

package com.zrgj.quartz.execution;

import com.zrgj.quartz.AbstractQuartzJob;
import com.zrgj.quartz.mybatis.pojo.SysJob;
import com.zrgj.quartz.util.JobInvokeUtil;
import com.zrgj.quartz.vo.SysJobVo;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

/**
 * 定时任务处理类 -禁止并发执行
 * @author 肘劉祁
 */
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, SysJobVo sysJob) throws Exception {
        JobInvokeUtil.invokeMethod(sysJob);
    }
}

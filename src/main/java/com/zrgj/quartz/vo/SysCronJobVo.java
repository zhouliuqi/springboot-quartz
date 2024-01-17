package com.zrgj.quartz.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zrgj.quartz.validator.valid.CronValid;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 肘劉祁
 * 时间表达式参数类
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SysCronJobVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "编号")
    private Integer cronJobId;

    @Schema(description = "任务编号")
    private Integer jobId;


    @Schema(description = "时间表达式",required = true)
    @CronValid
    private String cron;

    @Schema(description = "计划策略 0-默认 " +
            "1-立即触发执行,追赶上错失的次数 " +
            "2-不触发立即执行" +
            "3-触发立即执行")
    private Integer misfirePolicy=0;
}

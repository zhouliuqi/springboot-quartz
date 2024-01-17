package com.zrgj.quartz.mybatis.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zrgj.quartz.BasePojo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 时间表达式任务表
 * </p>
 *
 * @author 周刘奇
 * @since 2023-10-11
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_cron_job")
@Schema(name = "SysCronJob", description = "时间表达式任务表")
public class SysCronJob extends BasePojo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "编号")
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "任务编号")
    @TableField("JOB_ID")
    private Integer jobId;


    @Schema(description = "时间表达式")
    @TableField("CRON")
    private String cron;

    @Schema(description = "计划策略 0-默认 " +
            "1-立即触发执行,追赶上错失的次数 " +
            "2-不触发立即执行" +
            "3-触发立即执行")
    @TableField("MISFIRE_POLICY")
    private Integer misfirePolicy;

}

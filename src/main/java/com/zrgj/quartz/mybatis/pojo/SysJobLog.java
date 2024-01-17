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
 * 任务日志表
 * </p>
 *
 * @author 周刘奇
 * @since 2023-10-11
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_job_log")
@Schema(name = "SysJobLog", description = "$!{table.comment}")
public class SysJobLog extends BasePojo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "任务日志编号")
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "任务名称")
    @TableField("JOB_NAME")
    private String jobName;

    @Schema(description = "任务组名称")
    @TableField("JOB_GROUP")
    private String jobGroup;

    @Schema(description = "日志信息")
    @TableField("LOG_INFO")
    private String logInfo;

    @Schema(description = "错误信息")
    @TableField("ERROR_INFO")
    private String errorInfo;

    @Schema(description = "开始时间")
    @TableField("START_TIME")
    private Date startTime;

    @Schema(description = "结束时间")
    @TableField("END_TIME")
    private Date endTime;

    @Schema(description = "任务执行是否成功 0-否 1-是(默认)")
    @TableField("IS_SUCCESS")
    private Integer isSuccess;
}

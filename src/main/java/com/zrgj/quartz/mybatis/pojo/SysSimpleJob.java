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

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 普通任务表
 * </p>
 *
 * @author 周刘奇
 * @since 2023-10-11
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_simple_job")
@Schema(name = "SysSimpleJob", description = "$!{table.comment}")
public class SysSimpleJob extends BasePojo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "普通任务编号")
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "任务编号")
    @TableField("JOB_ID")
    private Integer jobId;

    @Schema(description = "执行次数 -1-无限制 0-默认")
    @TableField("COUNT")
    private Integer count;

    @Schema(description = "时间单位 0-毫秒(默认) 1-秒 2-分 3-时")
    @TableField("TIME_UNIT")
    private Integer timeUnit;

    @Schema(description = "时间间隔")
    @TableField("TIME_INTERVAL")
    private Integer timeInterval;

    @Schema(description = "计划策略 0-默认 " +
            "1-立即触发执行, 追赶上错失的次数 " +
            "2 - 无论什么情况一次性执行 3-不触发立即执行, 保持触发器的重复次数不变  " +
            "4 - 不触发立即执行, 重复次数修改为原本应该触发的总次数 " +
            "5 - 立即触发执行, 保持触发器的重复次数不变  " +
            "6 - 立即触发执行,, 重复次数修改为原本应该触发的总次数")
    @TableField("MISFIRE_POLICY")
    private Integer misfirePolicy;

}

package com.zrgj.quartz.vo;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 普通任务
 * </p>
 *
 * @author 周刘奇
 * @since 2023-10-11
 */
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SysSimpleJobVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "普通任务编号")
    private Integer simpleJobId;

    @Schema(description = "任务编号")
    private Integer jobId;

    @Schema(description = "执行次数 -1-无限制 0-默认")
    private Integer count=0;

    @Schema(description = "时间单位 0-毫秒(默认) 1-秒 2-分 3-时")
    private Integer timeUnit=0;

    @Schema(description = "时间间隔")
    @NotNull(message = "时间间隔不能为空")
    private Integer timeInterval;

    @Schema(description = "计划策略 0-默认 " +
            "1-立即触发执行, 追赶上错失的次数 " +
            "2 - 无论什么情况一次性执行 3-不触发立即执行, 保持触发器的重复次数不变  " +
            "4 - 不触发立即执行, 重复次数修改为原本应该触发的总次数 " +
            "5 - 立即触发执行, 保持触发器的重复次数不变  " +
            "6 - 立即触发执行,, 重复次数修改为原本应该触发的总次数")
    private Integer misfirePolicy=0;

}

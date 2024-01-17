package com.zrgj.quartz.mybatis.pojo;//package com.zrgj.quartz.pojo;
//
//import com.zrgj.common.vo.BasePojo;
//import io.swagger.v3.oas.annotations.media.Schema;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.experimental.Accessors;
//
//import java.util.Date;
//
///**
// * @author 肘劉祁
// */
//@EqualsAndHashCode(callSuper = true)
//@Data
//@Accessors(chain = true)
//public class SysJobDailyTimeInterval extends BasePojo {
//
//    @Schema(description = "编号")
//    private Integer jobDailyTimeIntervalId;
//
//    @Schema(description = "任务编号")
//    private Integer jobId;
//
//    @Schema(description = "是否立即执行 0-是 1-否")
//    private Integer isImmediate;
//
//    @Schema(description = "开始时间")
//    private Date startTime;
//
//    @Schema(description = "结束时间")
//    private Date endTime;
//
//    @Schema(description = "执行次数 0-无限制")
//    private Integer count;
//}

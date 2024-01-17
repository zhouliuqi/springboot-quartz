package com.zrgj.quartz.function.controller;

import com.zrgj.quartz.function.service.JobFunctionService;
import com.zrgj.quartz.vo.SysJobVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author 肘劉祁
 */
@RestController
@Tag(description = "定时任务",name = "定时任务")
@Validated
public class JobController {

    @Resource
    private JobFunctionService jobFunctionService;

    @PostMapping("insert/job")
    @Operation(summary = "添加任务",operationId = "1")
    public boolean insertJob(@Valid @RequestBody SysJobVo jobVo){
        return jobFunctionService.insertJob(jobVo);
    }


    @PostMapping("update/job")
    @Operation(summary = "更新任务",operationId = "1")
    public boolean updateJob(@Valid @RequestBody SysJobVo jobVo){
        return jobFunctionService.updateJob(jobVo);
    }


    @PostMapping("pause/job/{uuid}")
    @Operation(summary = "暂停任务",operationId = "1")
    public boolean pauseJob(@PathVariable("uuid")
                                @Parameter(description = "任务唯一标识")
                                String uuid){
        return jobFunctionService.pauseJob(uuid);
    }


    @PostMapping("resume/job/{uuid}")
    @Operation(summary = "恢复任务",operationId = "1")
    public boolean resumeJob(@PathVariable("uuid")
                                 @Parameter(description = "任务唯一标识")
                                         String uuid){
        return jobFunctionService.resumeJob(uuid);
    }

    @PostMapping("delete/job/{uuid}")
    @Operation(summary = "删除任务",operationId = "1")
    public boolean deleteJob(@PathVariable("uuid")
                                 @Parameter(description = "任务唯一标识")
                                         String uuid){
        return jobFunctionService.deleteJob(uuid);
    }

    @PostMapping("delete/job/uuids")
    @Operation(summary = "批量删除任务",operationId = "1")
    public boolean deleteJobByUuids(@RequestBody
                                        @Parameter(description = "任务唯一标识集合")
            @NotEmpty(message = "任务唯一标识集合不能为空")
                                    List<String> uuids){
        return jobFunctionService.deleteJobByUuids(uuids);
    }

    @PostMapping("run/job/{uuid}")
    @Operation(summary = "立即运行",operationId = "1")
    public boolean run(@PathVariable("uuid")
                           @Parameter(description = "任务唯一标识")
                                   String uuid){
        return jobFunctionService.run(uuid);
    }

    @PostMapping("get/job/details/{uuid}")
    @Operation(summary = "获取任务详情",operationId = "1")
    public SysJobVo getJobDetailsByUuId(@PathVariable("uuid")
                                            @Parameter(description = "任务唯一标识")
                                                    String uuid){
        return jobFunctionService.getJobDetailsByUuId(uuid);
    }

    @PostMapping("check/cron/expression/is/valid")
    @Operation(summary = "校验时间表达式是否有效",operationId = "1")
    public boolean checkCronExpressionIsValid(@RequestBody
                                                  @Parameter(description = "时间表达式")
                                                          String icon){
        return jobFunctionService.checkCronExpressionIsValid(icon);
    }

    @GetMapping("get/job/basic/info/list")
    @Operation(summary = "获取任务基本信息列表",operationId = "1")
    public List<SysJobVo> getJobBasicInfoList(){
        return jobFunctionService.getJobBasicInfoList();
    }



}

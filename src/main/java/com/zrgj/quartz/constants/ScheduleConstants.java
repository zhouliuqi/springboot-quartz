package com.zrgj.quartz.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 任务调度通用常量
 * @author 肘劉祁
 */

public class ScheduleConstants {

    /**
     * 任务调度参数key
     */
    public static final String TASK_CLASS_NAME = "TASK_CLASS_NAME";

    /**
     * 任务调度参数key
     */
    public static final String TASK_PROPERTIES = "TASK_PROPERTIES";

    /**
     * 定时任务白名单配置（仅允许访问的包名，如其他需要可以自行添加）
     */
    public static final String[] JOB_WHITELIST_STR = { "com.zrgj.quartz" };

    /**
     * 定时任务违规的字符
     */
    public static final String[] JOB_ERROR_STR = { "java.net.URL", "javax.naming.InitialContext", "org.yaml.snakeyaml",
            "org.springframework", "org.apache", "com.ruoyi.common.utils.file", "com.ruoyi.common.config" };

}

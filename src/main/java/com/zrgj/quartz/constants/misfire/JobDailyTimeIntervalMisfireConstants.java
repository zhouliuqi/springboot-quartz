package com.zrgj.quartz.constants.misfire;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author 肘劉祁
 */

public class JobDailyTimeIntervalMisfireConstants {

    @Getter
    @AllArgsConstructor
    public enum IntervalUnit {
        /**
         * 毫秒
         */
        MILLISECOND(0),
        /**
         * 秒
         */
        SECOND(1),
        /**
         * 分钟
         */
        MINUTE(2),
        /**
         * 小时
         */
        HOUR(3),
        /**
         * 一天
         */
        DAY(4),
        /**
         * 一周
         */
        WEEK(5),
        /**
         * 一月
         */
        MONTH(6),
        /**
         * 一年
         */
        YEAR(7);
        private final Integer value;
    }
}

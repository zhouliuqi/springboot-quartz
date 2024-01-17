package com.zrgj.quartz.constants.misfire;

/**
 * 时间表达式计划策略常量
 * @author 肘劉祁
 */

public class JobCronMisfireConstants {

    /**
     *当触发器错失触发时间时，调度器会尝试执行触发器的 updateAfterMisfire() 方法
     *这个方法会根据具体的触发器类型来确定如何处理错失情况
     * 默认
     */
    public final static int MISFIRE_INSTRUCTION_SMART_POLICY=0;

    /**
     * 如果触发器错失了多个预定的触发时间，使用这个指令后，触发器可能会迅速触发多次，以尽量补偿错失的次数。
     * 例如，如果一个每15秒触发一次的 SimpleTrigger 在错失了5分钟后有机会触发，它会立即执行20次，以“赶上”错失的次数
     */
    public final static int MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY=1;


    /**
     * 当触发器错失了触发时间后，它会告诉调度器：“请把我的下一次触发时间更新为当前时间之后的下一个合适的时间点，但不要立即触发我。”
     */
    public final static int MISFIRE_INSTRUCTION_DO_NOTHING=2;

    /**
     *那么无论发生了什么错失情况，触发器都希望立即执行，而不是等待下一个计划的时间点
     */
    public final static int MISFIRE_INSTRUCTION_FIRE_ONCE_NOW=3;
}

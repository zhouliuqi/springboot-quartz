package com.zrgj.quartz.constants.misfire;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 普通计划策略常量
 * @author 肘劉祁
 */

public class JobSimpleMisfireConstants {


    /**
     *当触发器错失触发时间时，调度器会尝试执行触发器的 updateAfterMisfire() 方法
     *这个方法会根据具体的触发器类型来确定如何处理错失情况
     * 默认
     */
    public final  static  int MISFIRE_INSTRUCTION_SMART_POLICY=0;

    /**
     * 如果触发器错失了多个预定的触发时间，使用这个指令后，触发器可能会迅速触发多次，以尽量补偿错失的次数。
     * 例如，如果一个每15秒触发一次的 SimpleTrigger 在错失了5分钟后有机会触发，它会立即执行20次，以“赶上”错失的次数
     */
    public final  static  int MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY=1;

    /**
     * 那么无论发生了什么错失情况，触发器都会告诉调度器：“我现在就要执行！"
     */
    public final  static  int MISFIRE_INSTRUCTION_FIRE_NOW=2;

    /**
     * 调度器触发器错失触发时间后，要重新安排触发器，等待下一个计划的时间点再触发，同时保持触发器的重复次数不变
     * 如果触发器的计划执行时间已经超过了触发器设定的结束时间，那么这个指令可能会导致触发器直接进入“COMPLETE”状态
     */
    public final  static  int MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT=3;

    /**
     *无论触发器错失了多少次触发时间，它都希望在当前时间之后的下一个合适的触发时间点重新触发，并且触发器的总重复次数会被设置为原本应该触发的总次数
     * 如果触发器的计划执行时间已经超过了触发器设定的结束时间，那么这个指令可能会导致触发器直接进入“COMPLETE”状态
     */
    public final  static  int MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT=4;

    /**
     *那么无论触发器错失了多少次触发时间，它都希望在当前时间重新触发，并且触发的总次数不变。
     *不过，需要注意的是，触发器会忽略原始设置的开始时间和总次数，这可能会影响到你以后需要查看原始设置值的情况。
     */
    public final  static  int MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT=5;

    /**
     *那么无论触发器错失了多少次触发时间，它都希望在当前时间重新触发，并且触发的总次数会被设置为原本应该触发的总次数。
     *但是，触发器的结束时间仍然有效，如果当前时间超过了结束时间，触发器将不再触发。
     */
    public final  static  int MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT=6;
}

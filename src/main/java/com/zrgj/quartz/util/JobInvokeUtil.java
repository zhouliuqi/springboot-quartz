package com.zrgj.quartz.util;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.zrgj.quartz.mybatis.pojo.SysJob;
import com.zrgj.quartz.vo.SysJobVo;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 任务执行工具类
 *
 * @author 肘劉祁
 */

public class JobInvokeUtil {

    /**
     * 执行方法
     *
     * @param sysJob 系统任务
     */
    public static void invokeMethod(SysJobVo sysJob) throws Exception {
        //获取调用目标字符串
        String invokeTarget = sysJob.getInvokeTarget();
        //获取bean名称
        String beanName = getBeanName(invokeTarget);
        //获取方法名称
        String methodName = getMethodName(invokeTarget);
        //获取方法参数
        List<Object[]> methodParams = getMethodParams(invokeTarget);
        //判断是否是包名
        if (!isValidClassName(beanName)) {
            //获取bean对象
            Object bean = SpringUtil.getBean(beanName);
            //执行bean方法
            invokeMethod(bean, methodName, methodParams);
        } else {
            //通过反射获取bean对象
            Object bean = Class.forName(beanName).getDeclaredConstructor().newInstance();
            //执行对象方法
            invokeMethod(bean, methodName, methodParams);
        }
    }

    /**
     * 调用任务方法
     *
     * @param bean         目标对象
     * @param methodName   方法名称
     * @param methodParams 方法参数
     */
    private static void invokeMethod(Object bean, String methodName, List<Object[]> methodParams)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        //判断参数是否为空
        if (ObjectUtil.isNotNull(methodParams)) {
            //获取对象方法
            Method method = bean.getClass().getMethod(methodName, getMethodParamsType(methodParams));
            //执行对象方法
            method.invoke(bean, getMethodParamsValue(methodParams));
        } else {
            //获取对象方法
            Method method = bean.getClass().getMethod(methodName);
            //执行对象方法
            method.invoke(bean);
        }
    }

    /**
     * 校验是否为为class包名
     *
     * @param invokeTarget 名称
     * @return true是 false否
     */
    public static boolean isValidClassName(String invokeTarget) {
        return StringUtils.countMatches(invokeTarget, ".") > 1;
    }

    /**
     * 获取bean名称
     *
     * @param invokeTarget 目标字符串
     * @return bean名称
     */
    public static String getBeanName(String invokeTarget) {
        //获取分隔符之前的数据
        String beanName = StringUtils.substringBefore(invokeTarget, "(");
        //获取分隔符最后出现位置之前的数据
        return StringUtils.substringBeforeLast(beanName, ".");
    }


    /**
     * 获取bean方法
     *
     * @param invokeTarget 目标字符串
     * @return method方法
     */
    public static String getMethodName(String invokeTarget) {
        //获取分隔符之前的数据
        String methodName = StringUtils.substringBefore(invokeTarget, "(");
        //获取分隔符最后出现位置之后的数据
        return StringUtils.substringAfterLast(methodName, ".");
    }


    /**
     * 获取method方法参数相关列表
     *
     * @param invokeTarget 目标字符串
     * @return method方法相关参数列表
     */
    public static List<Object[]> getMethodParams(String invokeTarget) {
        //获取分隔符之间的数据
        String methodStr = StringUtils.substringBetween(invokeTarget, "(", ")");
        //判断是否为空
        if (StringUtils.isBlank(methodStr)) {
            return null;
        }
        //拆分字符串
        String[] methodParams = methodStr.split(",(?=([^\"']*[\"'][^\"']*[\"'])*[^\"']*$)");
        //初始化参数集合
        List<Object[]> classes = new LinkedList<>();
        //遍历数据
        for (int i = 0; i < methodParams.length; i++) {
            //去掉空格
            String str = StringUtils.trimToEmpty(methodParams[i]);
            // String字符串类型，以'或"开头
            if (StringUtils.startsWithAny(str, "'", "\"")) {
                classes.add(new Object[]{StringUtils.substring(str, 1, str.length() - 1), String.class});
            }
            // boolean布尔类型，等于true或者false
            else if ("true".equalsIgnoreCase(str) || "false".equalsIgnoreCase(str)) {
                classes.add(new Object[]{Boolean.valueOf(str), Boolean.class});
            }
            // long长整形，以L结尾
            else if (StringUtils.endsWith(str, "L")) {
                classes.add(new Object[]{Long.valueOf(StringUtils.substring(str, 0, str.length() - 1)), Long.class});
            }
            // double浮点类型，以D结尾
            else if (StringUtils.endsWith(str, "D")) {
                classes.add(new Object[]{Double.valueOf(StringUtils.substring(str, 0, str.length() - 1)), Double.class});
            }
            // float，以F结尾
            else if (StringUtils.endsWith(str, "F")) {
                classes.add(new Object[]{Float.valueOf(StringUtils.substring(str, 0, str.length() - 1)), Float.class});
            }
            //判断是否整型
            else if (NumberUtil.isInteger(str)) {
                classes.add(new Object[]{Integer.valueOf(str), Integer.class});
            }
            // 其他类型归类为object
            else {
                classes.add(new Object[]{str, Object.class});
            }
        }
        return classes;
    }

    /**
     * 获取参数类型
     *
     * @param methodParams 参数相关列表
     * @return 参数类型列表
     */
    public static Class<?>[] getMethodParamsType(List<Object[]> methodParams) {
        //初始化数组
        Class<?>[] classes = new Class<?>[methodParams.size()];
        //遍历数据
        for (int i = 0; i < methodParams.size(); i++) {
            //获取对象数组
            Object[] objects = methodParams.get(i);
            //将数据添加到数组中
            classes[i] = (Class<?>) objects[1];
        }
        return classes;
    }

    /**
     * 获取参数值
     *
     * @param methodParams 参数相关列表
     * @return 参数值列表
     */
    public static Object[] getMethodParamsValue(List<Object[]> methodParams) {
        //初始化数组
        Object[] classes = new Object[methodParams.size()];
        //遍历数据
        for (int i = 0; i < methodParams.size(); i++) {
            //获取对象数组
            Object[] objects = methodParams.get(i);
            //将数据添加到数组中
            classes[i] = objects[0];
        }
        return classes;
    }
}

package cn.xisun.influxdb.bean.other;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/21 20:33
 * @description 名称的类别
 */
public enum NameType {
    // 目前有效的告警测点
    TAGALM,
    // 设置为不归档的测点
    TAGNotArchived,
    // 设置为不压缩的测点
    TAGNotCompress,
    // 设置了阶跃属性的测点
    TAGStep,
    // 设置了特殊变换因子的测点，不会包括scale为1且offset为0的变换因子
    TAGSpecialFactor,
    // 设置了实时数据订阅的测点
    RTDSubscribe,
    // 设置了实时方程式的测点
    EQ,
}

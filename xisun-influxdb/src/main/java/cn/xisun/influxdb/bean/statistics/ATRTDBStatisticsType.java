package cn.xisun.influxdb.bean.statistics;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/21 12:50
 * @description 统计类型
 * 所有统计均针对单个测点，需指定统计的起止时间
 */
public enum ATRTDBStatisticsType {
    // 总和
    SUM,
    // 最大值
    MAX,
    // 最小值
    MIN,
    // 平均值
    AVG,
    // 方差
    VAR,
    // 总体方差
    VARP,
    // 标准差
    STDEV,
    // 总体标准差
    STDEVP,
    // 个数
    COUNT,
    // 有效时间，以天为单位
    AVTIME,
    // 时间加权累积
    INTACC,
    // 变化次数
    VCOUNT,
    // 变化时间的累加，以天为单位
    VTIME,
    // 平均值与最大值的比率
    AVGRATIO,
    // 最小值与最大值的比率
    MINRATIO,
}

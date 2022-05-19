package cn.xisun.influxdb.bean.realTimeData;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/21 15:20
 * @description 数据的原始状态(2字节质量的最低4位)
 */
public enum ATRTDBOriginalStatus {
    // 正常
    GOOD,
    // 无数据
    NODATA,
    // 创建
    CREATED,
    // 停机
    SHUTDOWN,
    // 计算停止
    CALCOFF,
    // 坏点
    BAD,
    // 被零除
    DIVBYZERO,
    // 已被删除
    REMOVED,
    // 没有激活
    DISABLED,
}

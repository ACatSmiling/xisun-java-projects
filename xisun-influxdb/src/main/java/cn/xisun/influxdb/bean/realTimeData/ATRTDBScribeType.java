package cn.xisun.influxdb.bean.realTimeData;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/21 16:17
 * @description 订阅类别
 */
public enum ATRTDBScribeType {
    // 只有时间达到才通知
    TIMETYPE,
    // 只有次数达到才通知
    COUNTTYPE,
    // 次数和时间两个都达到才通知
    BOTH,
    // 次数或时间任意一个达到即通知
    EITHER,
}

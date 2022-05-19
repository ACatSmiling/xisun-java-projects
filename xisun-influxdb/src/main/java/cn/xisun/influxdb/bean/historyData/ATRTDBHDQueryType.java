package cn.xisun.influxdb.bean.historyData;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/21 17:20
 * @description 查询类型
 */
public enum ATRTDBHDQueryType {
    // 按时间查询原始数据(需要指定开始与结束时间)
    RawByTime,
    // 按数量查询原始数据(需要指定开始时间和个数)
    RawByNumber,
    // 查询插值
    Interpolated,
    // 查询趋势(用于绘图)
    Trend,
}

package cn.xisun.influxdb.bean.historyData;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/21 18:59
 * @description 扩展查询参数项
 */
public class ATRTDBQueryParamEx2Item {
    /**
     * 测点名
     */
    private String tagName;
    /**
     * 开始时间，格式为"YYYY-MM-DD HH:Mi:SS"
     * 包含毫秒则为"YYYY-MM-DD HH:Mi:SS.ms"
     */
    private String startTime;
    /**
     * 结束时间，格式同startTime
     */
    private String endTime;
    /**
     * 时间间隔的毫秒数
     */
    private Integer intervalByMs;
    /**
     * 采集数
     */
    private Integer numberOfSamples;
}

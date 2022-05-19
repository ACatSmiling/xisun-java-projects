package cn.xisun.influxdb.bean.realTimeData;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/21 16:45
 * @description 订阅项
 */
public class ATRTDBSubscribItem {
    /**
     * 测点名
     */
    private String tagName;
    /**
     * 订阅类别
     */
    private ATRTDBScribeType type;
    /**
     * 次数，10以上(含10)为有效
     */
    private Integer count;
    /**
     * 时间，秒为单位，300以上(含300)为有效
     */
    private Integer time;
}

package cn.xisun.influxdb.bean.historyData;

import java.util.List;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/21 18:09
 * @description 宽数据结果项
 */
public class WideDataResultItem {
    /**
     * 时间戳
     */
    private Double timestamp;

    /**
     * 值列表，sequence<double> ValueListType;
     */
    private List<Double> valueList;
}

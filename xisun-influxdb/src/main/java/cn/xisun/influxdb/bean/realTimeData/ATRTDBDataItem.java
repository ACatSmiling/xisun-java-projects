package cn.xisun.influxdb.bean.realTimeData;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/21 15:24
 * @description 普通数据项
 */
@Setter
@Getter
@ToString
public class ATRTDBDataItem {
    /**
     * 测点名称
     */
    private String tagName;
    /**
     * 测点质量，其值为ATRTDBOriginalStatus、ATRTDBDataSrc、OPC状态和ATRTDBCurStatus的组合
     */
    private Short quality;
    /**
     * 时间戳，从1899年12月30日午夜起计算"天"数，
     * 是OLE自动化的VARIANT数据类型的可能类型之一，表示一个绝对的日期时间值
     * 如42983.636197743050表示"2017-09-05 15:16:07.485"
     */
    private Double timestamp;
    /**
     * 测点值
     */
    private Double value;
}

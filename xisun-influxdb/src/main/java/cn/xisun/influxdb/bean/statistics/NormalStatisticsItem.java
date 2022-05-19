package cn.xisun.influxdb.bean.statistics;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/21 15:19
 * @description 常用统计项，V2.0新增
 */
public class NormalStatisticsItem {
    private String tagName;
    private Long count;
    private Double sum;
    private Double max;
    private Double min;
}

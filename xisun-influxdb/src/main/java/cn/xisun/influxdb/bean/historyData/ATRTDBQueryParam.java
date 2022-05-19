package cn.xisun.influxdb.bean.historyData;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/21 17:22
 * @description 查询参数
 */
@Setter
@Getter
@ToString
public class ATRTDBQueryParam {
    /**
     * 查询类型
     */
    private ATRTDBHDQueryType type;
    /**
     * 测点名称列表，sequence<string> ATRTDBTagNameList;
     */
    private List<String> tagNames;
    /**
     * 开始时间，格式为"YYYY-MM-DD HH:Mi:SS.ms"，最后的.ms可省略
     */
    private String startTime;
    /**
     * 结束时间，格式同startTime
     */
    private String endTime;
    /**
     * 时间间隔，以"毫秒"数计
     */
    private Integer intervalByMs;
    /**
     * 采样数量，即某一测点期待返回的条数
     */
    private Integer numberOfSamples;
}

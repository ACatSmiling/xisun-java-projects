package cn.xisun.influxdb.bean.historyData;

import java.util.List;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/21 19:04
 * @description 扩展查询参数项
 */
public class ATRTDBQueryParamEx2 {
    /**
     * 查询类型
     */
    private ATRTDBHDQueryType type;
    /**
     * 参数列表，sequence<ATRTDBQueryParamEx2Item> QueryParamEx2List;
     */
    private List<ATRTDBQueryParamEx2Item> paramList;
}

package cn.xisun.influxdb.bean.realTimeData;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/21 15:21
 * @description 数据的来源(2字节质量的最低4位)
 */
public enum ATRTDBDataSrc {
    // 现场读入的数据
    RAWDATA,
    // 手工录入的数据
    HANDIN,
    // 计算的数据，当表达式其中有一个变量状态不为0(正常)时，状态低4位设置为CALCOFF
    ALCULATED,
}

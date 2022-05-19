package cn.xisun.influxdb.bean.alarm;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/21 19:08
 * @description 告警配置类型
 */
public enum ATRTDBAlarmType {
    // 禁止告警
    Disable,
    // 开关量告警
    Switch,
    // 模拟量告警
    Analog,
    // 整型(离散量)告警，V1.9新增
    Integer,
}

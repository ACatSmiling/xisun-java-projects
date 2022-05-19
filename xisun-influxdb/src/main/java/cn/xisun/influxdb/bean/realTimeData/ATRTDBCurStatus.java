package cn.xisun.influxdb.bean.realTimeData;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/21 15:23
 * @description 数据当前状态
 */
public enum ATRTDBCurStatus {
    // 测点状态正常
    STSTUSNORMAL,
    // 模拟量超II级下限告警
    ALOWOVER2,
    // 模拟量超I级下限告警
    ALOWOVER1,
    // 模拟量超I级上限告警
    AUPOVER1,
    // 模拟量超II级上限告警
    AUPOVER2,
    // 开关量告警
    DALARM,
    // 非计量点模拟量长时间(如5分钟)不变，至目前尚不支持
    ANOCHANGE,
    //告警确认
    ALARMCONFIRM,
    // 质量不良，不判断
    NOTGOOD,
    // 命中告警脚本
    HITALARMSCRIPT,
}

package cn.xisun.influxdb.bean.alarm;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/21 19:11
 * @description 告警配置结构体
 * 模拟量告警中的超出下限与超出上限，应满足 lowOver2 <= lowOver1 < upOver1 <= upOver2，否则该设置无效
 */
@Setter
@Getter
@ToString
public class ATRTDBAlarmCnfg {
    /**
     * 测点名称
     */
    private String tagName;
    /**
     * 告警类型
     */
    private ATRTDBAlarmType type;
    /**
     * 属开关量告警，开关量为此值时告警
     */
    private Integer dAlarm;
    /**
     * 属模拟量告警，长时间不改变。目前尚不支持，保留。
     */
    private Integer noChange;
    /**
     * 属模拟量告警，超出 2 级下限
     */
    private Float lowOver2;
    /**
     * 属模拟量告警，超出 1 级下限
     */
    private Float lowOver1;
    /**
     * 属模拟量告警，超出 1 级上限
     */
    private Float upOver1;
    /**
     * 属模拟量告警，超出 2 级上限
     */
    private Float upOver2;
}

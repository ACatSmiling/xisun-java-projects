package cn.xisun.influxdb.bean.alarm;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/21 19:36
 * @description 扩展的告警配置结构体，V1.9新增
 */
@Setter
@Getter
@ToString
public class ATRTDBAlarmCnfgEx {
    /**
     * 测点名称
     */
    private String tagName;
    /**
     * 告警类型
     */
    private ATRTDBAlarmType type;
    /**
     * 开关量告警，开关量为此值时告警
     */
    private Integer dAlarm;
    /**
     * 长时间不改变告警，大于 0 时有效，单位秒
     */
    private Integer noChange;
    /**
     * 超出下限 2 级
     */
    private Float lowOver2;
    /**
     * 超出 1 级下限
     */
    private Float lowOver1;
    /**
     * 超出 1 级上限
     */
    private Float upOver1;
    /**
     * 超出 2 级上限
     */
    private Float upOver2;
    /**
     * 告警脚本，符合脚本的将告警。目前脚本只针对type为Integer的告警，具体格式请见接口说明
     */
    private String alarmScript;
}

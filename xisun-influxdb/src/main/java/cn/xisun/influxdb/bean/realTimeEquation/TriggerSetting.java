package cn.xisun.influxdb.bean.realTimeEquation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/21 19:49
 * @description 方程式计算触发设置
 */
@Setter
@Getter
@ToString
public class TriggerSetting {
    /**
     * 类别，目前不支持Timer方式
     */
    private TriggerType type;
    /**
     * 触发测点(type为RealTimeData时有效)
     */
    private String triggerTag;
    /**
     * 定时器方式触发的间隔秒数(type为Timer时有效)
     */
    private Integer intervalByS;
}

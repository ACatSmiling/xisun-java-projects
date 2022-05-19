package cn.xisun.influxdb.bean.realTimeEquation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/21 19:52
 * @description 方程式
 */
@Setter
@Getter
@ToString
public class Equation {
    /**
     * 测点
     */
    private String tag;
    /**
     * 脚本
     */
    private String script;
    /**
     * 触发设置
     */
    private TriggerSetting trigger;
}

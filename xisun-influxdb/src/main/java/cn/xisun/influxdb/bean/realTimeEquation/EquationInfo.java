package cn.xisun.influxdb.bean.realTimeEquation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/21 20:30
 * @description 方程式信息
 */
@Setter
@Getter
@ToString
public class EquationInfo {
    /**
     * 方程式
     */
    private Equation eq;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 运行状态
     */
    private EquationStatus status;
}

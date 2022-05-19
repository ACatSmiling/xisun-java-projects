package cn.xisun.influxdb.bean.realTimeEquation;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/21 20:24
 * @description 方程式状态
 */
public enum EquationStatus {
    // 就绪
    Ready,
    // 运行中
    Running,
    // 暂停中
    Suspend,
    // 已完成
    Complete,
}

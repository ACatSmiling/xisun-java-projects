package cn.xisun.influxdb.bean.data;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/21 12:49
 * @description 测点扩展属性：压缩选项
 * V1.9新增，对DoubleFloat、SwitchValue、SingleInteger、DoubleInteger类测点有效
 */
public enum CompressionOption {
    // 允许压缩，默认值
    EnableCompress,
    // 禁止压缩
    DisableCompress,
    // 未知，此选项备用
    Unknwon,
}

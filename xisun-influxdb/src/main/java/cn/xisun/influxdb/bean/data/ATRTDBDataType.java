package cn.xisun.influxdb.bean.data;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/21 10:30
 * @description 测点数据类型
 * 当前API中，模拟量使用DoubleFloat类型，开关量使用SwitchValue类型，开关量只支持写入1.0或0.0，如果写入其他值，结果未定义
 * 字符串类型应使用VariableString，长度不应超过256字节，不支持空串，字符串应使用UTF-8编码
 */
public enum ATRTDBDataType {
    // 不同工程单位间的换算比例
    Scaled,
    // 单精度浮点数，范围为-3.40E+38至+3.40E+38，精度为6至7位有效数字
    SingleFloat,
    // 双精度浮点数，范围为-1.79E+308至+1.79E+308，精度为15至16位有效数字
    DoubleFloat,
    // 2字节整型，范围为-32768至+32767
    SingleInteger,
    // 4字节整型，范围为-2147483648至+2147483647
    DoubleInteger,
    // 固定长度字符串
    FixedString,
    // 可变长度字符串，字符串选用此类型，应使用UTF-8编码,
    VariableString,
    // Biniary large object的简称，用于存储二进制大数据
    Blob,
    // 日期时间类型
    Time,
    // 开关值类型，使用1.0表示开，0.0表示关
    SwitchValue,
}

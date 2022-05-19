package cn.xisun.influxdb.bean.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/21 10:23
 * @description 测点基本属性
 */
@Setter
@Getter
@ToString
public class ATRTDBTagItem {
    /**
     * 测点名称
     */
    private String tagName;
    /**
     * 测点索引，8字节整型，应保持云端全局唯一，必须设置
     */
    private Long tagIndex;
    /**
     * 测点数据类型
     */
    private ATRTDBDataType dataType;
    /**
     * 备注
     */
    private String comment;
}

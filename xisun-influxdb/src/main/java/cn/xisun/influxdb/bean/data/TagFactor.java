package cn.xisun.influxdb.bean.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/21 11:09
 * @description 测点扩展属性：测点变化因子属性
 */
@Setter
@Getter
@ToString
public class TagFactor {
    /**
     * 缩放因子
     */
    private Double scale;
    /**
     * 偏移
     */
    private Double offset;
}

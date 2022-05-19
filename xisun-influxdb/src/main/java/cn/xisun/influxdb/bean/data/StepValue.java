package cn.xisun.influxdb.bean.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/21 11:08
 * @description 测点扩展属性：阶跃值
 */
@Setter
@Getter
@ToString
public class StepValue {
    /**
     * 阶跃类型
     */
    private StepType type;
    /**
     * 压缩偏差，当type为ON时，需要提供此值，要求此值大于0，一
     * 般建议为量程的0.1%至1%为宜，请根据业务需要合理设置
     */
    private float compdev;
}

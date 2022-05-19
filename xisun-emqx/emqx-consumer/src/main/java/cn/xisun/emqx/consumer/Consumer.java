package cn.xisun.emqx.consumer;

import cn.xisun.emqx.consumer.utils.MqttClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/5/6 11:13
 * @description 消费者主入口
 */
@Slf4j
public class Consumer {
    public static void main(String[] args) {
        try {
            MqttClientUtil.getMqttClient(null);
        } catch (MqttException e) {
            log.error("mqtt client connect exception", e);
        }
    }
}

package cn.xisun.emqx.consumer.callback;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/5/7 17:28
 * @description 回调函数
 */
@Slf4j
public class MessageHandlingCallbackExtended implements MqttCallbackExtended {
    private MqttClient mqttClient;
    private String topic;

    public MessageHandlingCallbackExtended() {

    }

    public MessageHandlingCallbackExtended(MqttClient mqttClient, String topic) {
        this.mqttClient = mqttClient;
        this.topic = topic;
    }

    @Override
    public void connectComplete(boolean reconnect, String serverUri) {
        // 断开重连之后，重新订阅主题
        log.debug("reconnect: {}, complete: {}", serverUri, reconnect);
        try {
            mqttClient.subscribe(topic);
        } catch (MqttException e) {
            log.error("mqtt client subscribe topic exception", e);
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        log.error("mqtt client connection lost", cause);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        MessageHandling.messageHandling(topic, message);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        log.debug("delivery complete");
    }
}

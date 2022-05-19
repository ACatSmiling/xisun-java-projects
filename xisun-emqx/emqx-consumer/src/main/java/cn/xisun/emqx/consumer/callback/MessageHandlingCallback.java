package cn.xisun.emqx.consumer.callback;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/5/6 11:36
 * @description
 */
@Slf4j
public class MessageHandlingCallback implements MqttCallback {


    private MqttClient mqttClient;
    private String topic;

    public MessageHandlingCallback() {

    }

    public MessageHandlingCallback(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    @Override
    public void connectionLost(Throwable throwable) {
        log.error("connection lost", throwable);
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        log.debug("message arrived, topic: {}, mqttMessage: {}", topic, mqttMessage.toString());
        MessageHandling.messageHandling(topic, mqttMessage);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        log.debug("deliveryComplete: {}", iMqttDeliveryToken.isComplete());
    }
}

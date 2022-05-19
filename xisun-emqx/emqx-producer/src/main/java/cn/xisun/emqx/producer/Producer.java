package cn.xisun.emqx.producer;

import cn.xisun.emqx.producer.bean.ATRTDBDataItem;
import cn.xisun.emqx.producer.utils.ArgumentsHandlerUtil;
import cn.xisun.emqx.producer.utils.FlexibleThreadPool;
import cn.xisun.emqx.producer.utils.MqttClientUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/5/5 10:47
 * @description 生产者主入口
 */
@Slf4j
public class Producer {
    public static void main(String[] args) {
        String[] tagNames = {"tag101", "tag102", "tag103", "tag104", "tag105", "tag106", "tag107",
                "tag108", "tag109", "tag110"};
        // 6个测点，消息全部发布到test_topic
        for (String tagName : tagNames) {
            FlexibleThreadPool.submitTask(() -> {
                try {
                    MqttClient mqttClient = MqttClientUtil.getMqttClient(null);
                    // noinspection InfiniteLoopStatement
                    while (true) {
                        ATRTDBDataItem atrtdbDataItem = new ATRTDBDataItem();
                        // 测点名
                        atrtdbDataItem.setTagName(tagName);
                        // 测点质量，统一定义为0
                        atrtdbDataItem.setQuality((short) 0);
                        // 测点值，随机的一个Double值，范围为[10, 70)
                        atrtdbDataItem.setValue(10 + ThreadLocalRandom.current().nextDouble(60));
                        // 测点时间戳，由当前时间的毫秒数转换而来
                        long now = Instant.now().toEpochMilli();
                        atrtdbDataItem.setTimestamp(ArgumentsHandlerUtil.transferMilliSecondsToOleTime(now));
                        log.debug("client id: {}, message: {}", mqttClient.getClientId(), atrtdbDataItem);

                        MqttMessage mqttMessage = new MqttMessage();
                        // 设置消息的服务质量
                        mqttMessage.setQos(0);
                        // 设置消息的payload
                        mqttMessage.setPayload(JSON.toJSONBytes(atrtdbDataItem));
                        // 发布消息
                        mqttClient.publish("antai/tsdb/test", mqttMessage);

                        // 隔15min发布一次新消息
                        try {
                            TimeUnit.SECONDS.sleep(15);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (MqttException e) {
                    log.error("mqtt exception: ", e);
                }
                return null;
            });

            // 隔10秒开启下一个测点
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            // 等待所有线程执行完毕当前任务
            FlexibleThreadPool.getThreadPool().shutdown();

            boolean loop;
            do {
                // 等待所有线程执行完毕，当前任务结束，等待2秒
                loop = !FlexibleThreadPool.getThreadPool().awaitTermination(2, TimeUnit.SECONDS);
            } while (loop);

            if (!loop) {
                log.info("all threads have finished executing");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

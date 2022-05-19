package cn.xisun.emqx.consumer.callback;

import cn.hutool.core.util.StrUtil;
import cn.xisun.emqx.consumer.bean.ATRTDBDataItem;
import cn.xisun.emqx.consumer.triger.BatchTimeTrigger;
import cn.xisun.emqx.consumer.utils.ArgumentsHandlerUtil;
import cn.xisun.emqx.consumer.utils.FlexibleThreadPool;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/5/10 10:50
 * @description 消息处理
 */
@Slf4j
public class MessageHandling {
    public static final List<ATRTDBDataItem> ATRTDB_TAG_ITEMS = Collections.synchronizedList(
            new ArrayList<>(5000));

    public static final int BATCH_SIZE = 10;

    public static void messageHandling(String topic, MqttMessage mqttMessage) {
        String message = new String(mqttMessage.getPayload());
        log.debug("mqtt message arrived, topic: {}, Qos: {}, content is: {}", topic, mqttMessage.getQos(), message);
        ATRTDBDataItem atrtdbTagItem = JSON.parseObject(message, ATRTDBDataItem.class);
        log.debug("mqtt message parsing end, object is: {}", atrtdbTagItem);
        // 参数验证，验证成功的对象，添加到缓存，验证失败的对象，直接丢弃
        boolean filter = ArgumentsHandlerUtil.filter(atrtdbTagItem, atrtdbDataItem -> {
            if (StrUtil.isEmpty(atrtdbDataItem.tagName)) {
                log.debug("atrtdbDataItem's tagName is illegal, and the current message will be discarded!");
                return false;
            }
            if (atrtdbDataItem.timestamp == 0.0) {
                log.debug("atrtdbDataItem's timestamp is illegal, and the current message will be discarded!");
                return false;
            }
            return true;
        });
        if (filter) {
            ATRTDB_TAG_ITEMS.add(atrtdbTagItem);
        }
        // 批触发器
        if (ATRTDB_TAG_ITEMS.size() >= BATCH_SIZE) {
            FlexibleThreadPool.submitTask(BatchTimeTrigger::batchTrigger);
        }
    }
}

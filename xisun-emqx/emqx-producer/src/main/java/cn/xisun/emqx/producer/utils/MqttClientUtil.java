package cn.xisun.emqx.producer.utils;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Properties;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/29 9:54
 * @description MQTT客户端工具类
 */
@Slf4j
public class MqttClientUtil {
    private static String localBroker;
    private static String clientId;
    private static MqttConnectOptions mqttConnectOptions;

    static {
        try {
            InputStream is = MqttClientUtil.class.getClassLoader().getResourceAsStream("mqtt.properties");
            Properties pros = new Properties();
            pros.load(is);

            // 读取配置信息
            localBroker = pros.getProperty("mqtt.broker.local");
            // 客户端Id添加时间戳，防止重复
            clientId = pros.getProperty("mqtt.clientId");
            String keepAliveInterval = pros.getProperty("mqtt.keepAliveInterval");
            String connectionTimeOut = pros.getProperty("mqtt.connectionTimeOut");
            String username = pros.getProperty("mqtt.username");
            String password = pros.getProperty("mqtt.password");

            // 设置默认连接参数
            mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setCleanSession(Boolean.TRUE);
            mqttConnectOptions.setKeepAliveInterval(Integer.parseInt(keepAliveInterval));
            mqttConnectOptions.setConnectionTimeout(Integer.parseInt(connectionTimeOut));
            mqttConnectOptions.setUserName(username);
            mqttConnectOptions.setPassword(password.toCharArray());
            mqttConnectOptions.setAutomaticReconnect(true);
        } catch (IOException e) {
            log.error("load mqtt properties failed!", e);
        }
    }

    private MqttClientUtil() {

    }

    /**
     * 根据连接信息创建mqtt client
     *
     * @param mqttConnectOptions 自定义连接信息
     * @return mqtt client
     * @throws MqttException 异常抛出，上层处理
     */
    public static MqttClient getMqttClient(MqttConnectOptions mqttConnectOptions) throws MqttException {
        // 创建客户端
        MqttClient mqttClient = new MqttClient(localBroker, clientId + "_" + Instant.now().toEpochMilli(),
                new MemoryPersistence());
        log.debug("mqtt client address: {}", mqttClient);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.debug("Execute Shutdown Hook: {}", mqttClient);
            MqttClientUtil.closeMqttClient(mqttClient);
        }));
        // 创建连接
        if (mqttConnectOptions != null) {
            mqttClient.connect(mqttConnectOptions);
        } else {
            mqttClient.connect(MqttClientUtil.mqttConnectOptions);
        }
        return mqttClient;
    }

    /**
     * 关闭mqtt client
     *
     * @param mqttClient 待关闭的mqtt client
     */
    public static void closeMqttClient(MqttClient mqttClient) {
        if (mqttClient != null) {
            try {
                mqttClient.disconnect();
            } catch (MqttException e) {
                e.printStackTrace();
            }
            try {
                mqttClient.close();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }
}

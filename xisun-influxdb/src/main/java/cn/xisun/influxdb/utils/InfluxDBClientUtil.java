package cn.xisun.influxdb.utils;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/2 9:10
 * @description InfluxDB客户端工具类
 */
public class InfluxDBClientUtil {
    private static volatile InfluxDBClient influxDBClient = null;

    private InfluxDBClientUtil() {

    }

    public static InfluxDBClient getInfluxDBClient() {
        if (influxDBClient == null) {
            synchronized (InfluxDBClientUtil.class) {
                if (influxDBClient == null) {
                    try {
                        // 1.加载配置文件
                        InputStream is = InfluxDBClientUtil.class.getClassLoader().getResourceAsStream("influxdb.properties");
                        Properties pros = new Properties();
                        pros.load(is);

                        // 2.读取配置信息
                        String url = pros.getProperty("influxdb.url");
                        String token = pros.getProperty("influxdb.token");
                        String org = pros.getProperty("influxdb.org");
                        String bucket = pros.getProperty("influxdb.bucket");

                        // 3.创建客户端对象实例
                        influxDBClient = InfluxDBClientFactory.create(url, token.toCharArray(), org, bucket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return influxDBClient;
    }
}

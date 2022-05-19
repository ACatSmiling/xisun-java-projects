package cn.xisun.influxdb.service;

import cn.xisun.influxdb.utils.InfluxDBClientUtil;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.write.Point;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/2 9:33
 * @description InfluxDB服务
 */
@Slf4j
public class InfluxDBService {
    private static final InfluxDBClient INFLUXDB_CLIENT = InfluxDBClientUtil.getInfluxDBClient();

    /**
     * 测试InfluxDB是否连通
     */
    public static void ping() {
        Boolean ping = INFLUXDB_CLIENT.ping();
        log.info("the result of joining influxdb: {}", ping);
    }

    /**
     * 同步写一条数据
     *
     * @param writeApiBlocking 同步写API
     * @param point            数据
     */
    public static void writeSynchronous(WriteApiBlocking writeApiBlocking, Point point) {
        writeApiBlocking.writePoint(point);
    }

    /**
     * 异步写一条数据
     *
     * @param writeApi 异步写API
     * @param point    数据
     */
    public static void writeAsynchronous(WriteApi writeApi, Point point) {
        writeApi.writePoint(point);
    }

    /**
     * 同步写数据集合
     *
     * @param writeApiBlocking 同步写API
     * @param points           数据集合
     */
    public static void writesSynchronous(WriteApiBlocking writeApiBlocking, List<Point> points) {
        writeApiBlocking.writePoints(points);
    }

    /**
     * 异步写数据集合
     *
     * @param writeApi 异步写API
     * @param points   数据集合
     */
    public static void writesAsynchronous(WriteApi writeApi, List<Point> points) {
        writeApi.writePoints(points);
    }

    /**
     * 按条件异步查询
     *
     * @param flux 查询条件
     */
    public static void query(String flux) {
        QueryApi queryApi = INFLUXDB_CLIENT.getQueryApi();
        queryApi.query(flux,
                (cancellable, fluxRecord) -> {
                    log.info("measurement: {}, tags_location: {}, _start: {}, _stop: {}, _field: {}, _value: {}, count: {}",
                            fluxRecord.getMeasurement(), fluxRecord.getValueByKey("location"), fluxRecord.getStart(),
                            fluxRecord.getStop(), fluxRecord.getValueByKey("_field"), fluxRecord.getValueByKey("_value"),
                            fluxRecord.getValue());
                },
                // 查询异常
                Throwable::printStackTrace,
                // 查询完成
                () -> log.debug("the query complete"));
        try {
            // 查询等待时间，需设置，否则可能上一次查询未结束，下一次查询开始了，会导致没有结果
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

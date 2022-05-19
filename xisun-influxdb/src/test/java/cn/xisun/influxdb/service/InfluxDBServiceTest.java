package cn.xisun.influxdb.service;

import cn.xisun.influxdb.utils.InfluxDBClientUtil;
import com.influxdb.client.*;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import io.reactivex.BackpressureOverflowStrategy;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/2 9:52
 * @description InfluxDBService测试类
 */
@Slf4j
class InfluxDBServiceTest {

    // 测试是否连通
    @Test
    void testPing() {
        InfluxDBService.ping();
    }

    // 同步写操作：Point
    @Test
    void testWriteSynchronous() {
        // 1.获取客户端
        InfluxDBClient influxDBClient = InfluxDBClientUtil.getInfluxDBClient();
        WriteApiBlocking writeApiBlocking = influxDBClient.getWriteApiBlocking();

        // 2.业务处理
        long start = System.currentTimeMillis();
        int dataNumbers = 100000;
        for (int i = 1; i <= dataNumbers; i++) {
            // 2.1.随机获得四个方位其中之一
            String[] locationAll = {"East", "North", "West", "South"};
            int locationIndex = new Random().nextInt(locationAll.length);
            String location = locationAll[locationIndex];
            // 2.2.获得10~60之间的随机数，保留两位数字
            Double temperature = 10 + Math.random() * (60 - 10 + 1);
            DecimalFormat df = new DecimalFormat("0.00");
            // 2.3.构建Point
            Point point = Point.measurement("temperature_collect")
                    .addTag("location", location)
                    .addField("value", df.format(temperature))
                    .time(Instant.now().toEpochMilli(), WritePrecision.MS);
            log.debug("data that needs to be written to influxdb: {}", point.toLineProtocol());
            // 2.4.写入数据到InfluxDB
            InfluxDBService.writeSynchronous(writeApiBlocking, point);
        }
        long end = System.currentTimeMillis();
        // data numbers: 100000, processing time: 410716 ms
        log.info("data numbers: {}, processing time: {} ms", dataNumbers, (end - start));

        // 3.关闭客户端
        influxDBClient.close();
    }

    // 同步写操作：List<Point>
    @Test
    void testWritesSynchronous() {
        // 1.获取客户端
        InfluxDBClient influxDBClient = InfluxDBClientUtil.getInfluxDBClient();
        WriteApiBlocking writeApiBlocking = influxDBClient.getWriteApiBlocking();

        // 2.业务处理
        long start = System.currentTimeMillis();
        List<Point> points = new ArrayList<>();
        int dataNumbers = 100000;
        for (int i = 1; i <= dataNumbers; i++) {
            // 2.1.随机获得四个方位其中之一
            String[] locationAll = {"East", "North", "West", "South"};
            int locationIndex = new Random().nextInt(locationAll.length);
            String location = locationAll[locationIndex];
            // 2.2.获得10~60之间的随机数，保留两位数字
            Double temperature = 10 + Math.random() * (60 - 10 + 1);
            DecimalFormat df = new DecimalFormat("0.00");
            // 2.3.构建Line Protocl协议数据
            Point point = Point.measurement("temperature_collect")
                    .addTag("location", location)
                    .addField("value", df.format(temperature))
                    .time(Instant.now().toEpochMilli(), WritePrecision.MS);
            log.debug("data that needs to be written to influxdb: {}", point.toLineProtocol());
            points.add(point);
            // 2.4.每10000条写入一次
            if (i % 10000 == 0) {
                InfluxDBService.writesSynchronous(writeApiBlocking, points);
                // 清空values
                points.clear();
            }
            // 设置每条数据时间戳的间隔，时间戳相同的数据，后写入会的覆盖前面的
            try {
                TimeUnit.NANOSECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        // data numbers: 100000, processing time: 148943 ms
        log.info("data numbers: {}, processing time: {} ms", dataNumbers, (end - start));

        // 3.关闭客户端
        influxDBClient.close();
    }

    // 异步写操作：Point
    @Test
    void testWriteAsynchronous() {
        // 1.获取客户端
        InfluxDBClient influxDBClient = InfluxDBClientUtil.getInfluxDBClient();
        WriteApi writeApi = influxDBClient.makeWriteApi(WriteOptions.builder()
                .batchSize(5000)
                .flushInterval(1000)
                .backpressureStrategy(BackpressureOverflowStrategy.DROP_OLDEST)
                .bufferLimit(10000)
                .jitterInterval(1000)
                .retryInterval(5000)
                .build());

        // 2.业务处理
        long start = System.currentTimeMillis();
        int dataNumbers = 100000;
        for (int i = 1; i <= dataNumbers; i++) {
            // 2.1.随机获得四个方位其中之一
            String[] locationAll = {"East", "North", "West", "South"};
            int locationIndex = new Random().nextInt(locationAll.length);
            String location = locationAll[locationIndex];
            // 2.2.获得10~60之间的随机数，保留两位数字
            Double temperature = 10 + Math.random() * (60 - 10 + 1);
            DecimalFormat df = new DecimalFormat("0.00");
            String format = df.format(temperature);
            // 2.3.构建Point
            Point point = Point.measurement("temperature_collect")
                    .addTag("location", location)
                    .addField("value", format)
                    .time(Instant.now().toEpochMilli(), WritePrecision.MS);
            log.debug("data that needs to be written to influxdb: {}", point.toLineProtocol());
            // 2.4.写入数据到InfluxDB
            InfluxDBService.writeAsynchronous(writeApi, point);
            // 设置每条数据时间戳的间隔，时间戳相同的数据，后写入的会覆盖前面的
            try {
                TimeUnit.NANOSECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        // data numbers: 100000, processing time: 147045 ms
        log.info("data numbers: {}, processing time: {} ms", dataNumbers, (end - start));

        // 3.关闭客户端
        writeApi.close();
        influxDBClient.close();
    }

    // 异步写操作：List<Point>
    @Test
    void testWritesAsynchronous() {
        // 1.获取客户端
        InfluxDBClient influxDBClient = InfluxDBClientUtil.getInfluxDBClient();
        WriteApi writeApi = influxDBClient.makeWriteApi(WriteOptions.builder()
                .batchSize(5000)
                .flushInterval(1000)
                .backpressureStrategy(BackpressureOverflowStrategy.DROP_OLDEST)
                .bufferLimit(10000)
                .jitterInterval(1000)
                .retryInterval(5000)
                .build());

        // 2.业务处理
        long start = System.currentTimeMillis();
        List<Point> points = new ArrayList<>();
        int dataNumbers = 100000;
        for (int i = 1; i <= dataNumbers; i++) {
            // 2.1.随机获得四个方位其中之一
            String[] locationAll = {"East", "North", "West", "South"};
            int locationIndex = new Random().nextInt(locationAll.length);
            String location = locationAll[locationIndex];
            // 2.2.获得10~60之间的随机数，保留两位数字
            Double temperature = 10 + Math.random() * (60 - 10 + 1);
            DecimalFormat df = new DecimalFormat("0.00");
            String format = df.format(temperature);
            // 2.3.构建Point
            Point point = Point.measurement("temperature_collect")
                    .addTag("location", location)
                    .addField("value", format)
                    .time(Instant.now().toEpochMilli(), WritePrecision.MS);
            log.debug("data that needs to be written to influxdb: {}", point.toLineProtocol());
            points.add(point);
            // 2.4.每10000条写入一次
            if (i % 10000 == 0) {
                InfluxDBService.writesAsynchronous(writeApi, points);
                // 清空values
                points.clear();
            }
            // 设置每条数据时间戳的间隔，时间戳相同的数据，后写入会的覆盖前面的
            try {
                TimeUnit.NANOSECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        // data numbers: 100000, processing time: 154423 ms
        log.info("data numbers: {}, processing time: {} ms", dataNumbers, (end - start));

        // 3.关闭客户端
        writeApi.close();
        influxDBClient.close();
    }

    // 异步写Api写入性能测试，改变batchSize和flushInterval不影响结果，大致为1.5ms一条数据
    @Test
    void testWriteAsynchronousPerformance() {
        // 1.获取客户端
        InfluxDBClient influxDBClient = InfluxDBClientUtil.getInfluxDBClient();
        WriteApi writeApi = influxDBClient.makeWriteApi(WriteOptions.builder()
                .batchSize(5000)
                .flushInterval(1000)
                .backpressureStrategy(BackpressureOverflowStrategy.DROP_OLDEST)
                .bufferLimit(10000)
                .jitterInterval(1000)
                .retryInterval(5000)
                .build());

        // 2.业务处理
        long start = System.currentTimeMillis();
        int dataNumbers = 100000;
        for (int i = 1; i <= dataNumbers; i++) {
            Point point = Point.measurement("temperature_collect")
                    .addTag("location", "East")
                    .addField("value", "25.00")
                    .time(Instant.now().toEpochMilli(), WritePrecision.MS);
            log.debug("data that needs to be written to influxdb: {}", point.toLineProtocol());
            InfluxDBService.writeAsynchronous(writeApi, point);
            // 设置每条数据时间戳的间隔，时间戳相同的数据，后写入会的覆盖前面的
            try {
                TimeUnit.NANOSECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        log.info("data numbers: {}, processing time: {} ms", dataNumbers, (end - start));

        // 3.关闭客户端
        writeApi.close();
        influxDBClient.close();
    }

    // 查询操作：Flux语法
    @Test
    void testQuery() {
        // 查询基础信息，必需
        String baseMessage = "from(bucket: \"xisun_influx_bucket\")\n" +
                " |> range(start: -10d)\n" +
                " |> filter(fn: (r) => r[\"_measurement\"] == \"temperature_collect\")";

        // 统计，会根据tags分组统计
        String flux_count = " |> count(column: \"_value\")";

        // 排序，根据tags和field
        String flux_sort = " |> sort(columns: [\"location\", \"_value\"])";

        // 分页
        String flux_limit = " |> limit(n:10)";

        InfluxDBService.query(baseMessage + flux_count);
    }
}
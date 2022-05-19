package cn.xisun.influxdb.service;

import cn.xisun.influxdb.bean.data.ATRTDBDataType;
import cn.xisun.influxdb.bean.historyData.ATRTDBHDQueryType;
import cn.xisun.influxdb.bean.historyData.ATRTDBQueryParam;
import cn.xisun.influxdb.bean.realTimeData.ATRTDBDataItem;
import cn.xisun.influxdb.utils.ArgumentsHandlerUtils;
import cn.xisun.influxdb.utils.InfluxDBClientUtil;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApi;
import com.influxdb.client.WriteOptions;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import io.reactivex.BackpressureOverflowStrategy;
import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/21 10:07
 * @description InfluxDB服务，对应安泰“数据存取API V2.0接口说明-2020-12-31文档”
 */
@Slf4j
public class InfluxDBAntaiService {
    private static final InfluxDBClient INFLUXDB_CLIENT = InfluxDBClientUtil.getInfluxDBClient();
    private static final WriteApi WRITE_API = INFLUXDB_CLIENT.makeWriteApi(WriteOptions.builder()
            .batchSize(5000)
            .flushInterval(1000)
            .backpressureStrategy(BackpressureOverflowStrategy.DROP_OLDEST)
            .bufferLimit(10000)
            .jitterInterval(1000)
            .retryInterval(5000)
            .build());
    private static final QueryApi QUERY_API = INFLUXDB_CLIENT.getQueryApi();
    private static final Integer NUMBER = 5000;

    /**
     * 批量生成一批数据到InfluxDB数据库，充当原始数据
     *
     * @deprecated 最初数据结构理解有误，以下方法作废
     */
    @Deprecated
    public static void createMeasurementPointsAtFirst() {
        // 1 业务处理
        long start = System.currentTimeMillis();
        List<Point> points = new ArrayList<>();
        int dataNumbers = 100000;
        long index1 = 0;
        long index2 = 0;
        long index3 = 0;
        long index4 = 0;
        String[] tagNameAll = {"JNWLC0601D00101M", "JX3-1F1-JSJ-006", "JX3-2F1-0HB-005", "HFSDJZX-NEW-01-04-03-07"};
        DecimalFormat df = new DecimalFormat("0.00");
        String chars = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 1; i <= dataNumbers; i++) {
            // 最终写入数据库的值
            String tagIndex = null;
            String fieldValue = null;

            // 1.1 随机获得四个测点名其中之一
            String tagName = tagNameAll[new Random().nextInt(tagNameAll.length)];

            // 1.2 每个测点的索引值，按递增设置，保持每个测点的索引是唯一的，每个测点的值分开设置
            switch (tagName) {
                case "JNWLC0601D00101M":
                    index1++;
                    // 测点1：取值对象设为模拟量，DoubleFloat类型，双精度浮点数，范围为-1.79E+308 至+1.79E+308
                    // 随机获得10(含)到70(不含)范围内的温度，保留两位小数
                    double temperature = 10 + ThreadLocalRandom.current().nextDouble(60);

                    tagIndex = String.valueOf(index1);
                    fieldValue = df.format(temperature);
                    break;
                case "JX3-1F1-JSJ-006":
                    index2++;
                    // 测点2：取值对象设为开关量，SwitchValue类型，值取0.0或1.0
                    // 随机获得开关值
                    double[] switchAll = {0.0, 1.0};
                    double switchValue = switchAll[new Random().nextInt(switchAll.length)];

                    tagIndex = String.valueOf(index2);
                    fieldValue = String.valueOf(switchValue);
                    break;
                case "JX3-2F1-0HB-005":
                    index3++;
                    // 测点3：取值对象设为字符串，VariableString类型，长度可变
                    // 随机获取字符串的长度：1 ≤ length ≤ 25内的整数
                    int length = 1 + ThreadLocalRandom.current().nextInt(chars.length() - 1);
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < length; j++) {
                        // 随机获取字符串中的字符
                        int location = ThreadLocalRandom.current().nextInt(chars.length());
                        sb.append(chars.charAt(location));
                    }

                    tagIndex = String.valueOf(index3);
                    fieldValue = sb.toString();
                    break;
                case "HFSDJZX-NEW-01-04-03-07":
                    index4++;
                    // 测点4：取值对象设为4字节整型，DoubleInteger类型，范围为-2147483648至+2147483647
                    // 随机获得一个0(含)到100000(不含)之间的整数
                    int value = ThreadLocalRandom.current().nextInt(100000);

                    tagIndex = String.valueOf(index4);
                    fieldValue = String.valueOf(value);
                    break;
                default:
                    break;
            }

            // 1.3 构建Point
            Point point = Point.measurement(tagName)
                    .addTag("tagIndex", tagIndex)
                    .addField("value", fieldValue)
                    .time(Instant.now().toEpochMilli(), WritePrecision.MS);
            log.debug("data that needs to be written to influxdb: {}", point.toLineProtocol());
            points.add(point);

            // 2.4.每5000条写入一次
            if (i % 5000 == 0) {
                WRITE_API.writePoints(points);
                // 清空values
                points.clear();
            }

            try {
                // 每条数据间隔设置为1纳秒
                TimeUnit.NANOSECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        log.info("data numbers: {}, processing time: {} ms", dataNumbers, (end - start));

        // 2.关闭客户端
        WRITE_API.close();
        INFLUXDB_CLIENT.close();
    }

    /**
     * 批量生成一批测点，测点写入tag_measurement中
     */
    public static void createAtRtDbTagItem() {
        // 四个基础测点
        String[] tagNameAll = {"JNWLC0601D00101M", "JX3-1F1-JSJ", "SDTSG-0HB", "HFSDJZX-NEW-01-04-03"};
        for (String tagName : tagNameAll) {
            String tagType, comment;
            switch (tagName) {
                case "JNWLC0601D00101M":
                    tagType = String.valueOf(ATRTDBDataType.DoubleFloat);
                    comment = "the series of JNWLC0601D00101M";
                    break;
                case "JX3-1F1-JSJ":
                    tagType = String.valueOf(ATRTDBDataType.SwitchValue);
                    comment = "the series of JX3-1F1-JSJ";
                    break;
                case "SDTSG-0HB":
                    tagType = String.valueOf(ATRTDBDataType.VariableString);
                    comment = "the series of SDTSG-0HB";
                    break;
                case "HFSDJZX-NEW-01-04-03":
                    tagType = String.valueOf(ATRTDBDataType.DoubleInteger);
                    comment = "the series of HFSDJZX-NEW-01-04-03";
                    break;
                default:
                    tagType = "undefined";
                    comment = "undefined";
                    break;
            }
            // 每一个基础测点，生成10个具体测点
            int addition = 101;
            for (int i = 0; i < 10; i++) {
                Point point = Point.measurement("tag_measurement")
                        .addTag("tagIndex", String.valueOf(addition))
                        .addTag("comment", comment)
                        .addTag("tagType", tagType)
                        .addField("tagName", tagName + "-" + addition)
                        .time(Instant.now().toEpochMilli(), WritePrecision.MS);
                log.debug("data that needs to be written to influxdb: {}", point.toLineProtocol());
                WRITE_API.writePoint(point);
                addition++;
                try {
                    // 每条数据间隔设置为500纳秒
                    TimeUnit.NANOSECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        WRITE_API.close();
        INFLUXDB_CLIENT.close();
    }

    /**
     * 获取测点总数
     *
     * @return 测点总数
     */
    public static int queryAtRtDbTagItemCounts() {
        String flux = "from(bucket: \"xisun_influx_bucket\")\n" +
                " |> range(start: 2022-03-16T08:00:00Z, stop: 2022-04-30T08:00:00Z)\n" +
                " |> filter(fn: (r) => r[\"_measurement\"] == \"tag_measurement\")" +
                " |> count(column: \"_value\")";
        int[] count = {0};
        QUERY_API.query(flux, (cancellable, fluxRecord) -> {
                    // 当前测点的信息，因为每个测点tag不同，一个测点会是一条记录
                    log.debug("current atrtdb tag name: {}, tag index: {}, tag type: {}, comment: {}",
                            fluxRecord.getMeasurement(), fluxRecord.getValueByKey("tagIndex"),
                            fluxRecord.getValueByKey("tagType"), fluxRecord.getValueByKey("comment"));
                    count[0]++;
                },
                Throwable::printStackTrace,
                () -> log.debug("the query complete"));
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return count[0];
    }

    /**
     * 写入实时数据，一次写入不应超过5000条
     * <p>
     * int RTDWriteEx(ATRTDBDataList dataList, out ATRTDBErrorList errors)
     * 普通实时数据库数据项列表：sequence<ATRTDBDataItem> ATRTDBDataList;
     *
     * @param writeApi        异步写api
     * @param atrtdbDataItems 实时数据集
     * @return 返回添加数据的条数
     */
    public static Integer writeRealTimeData(WriteApi writeApi, List<ATRTDBDataItem> atrtdbDataItems) {
        if (atrtdbDataItems.size() > NUMBER) {
            log.warn("atrtdbDataItems size over 5000, it should be shorter!");
        }
        List<Point> points = new ArrayList<>();
        for (ATRTDBDataItem atrtdbDataItem : atrtdbDataItems) {
            String tagName = atrtdbDataItem.getTagName();
            Short quality = atrtdbDataItem.getQuality();
            Double value = atrtdbDataItem.getValue();
            Double timestamp = atrtdbDataItem.getTimestamp();
            ArgumentsHandlerUtils.checkNotNull(tagName, "tagName");
            ArgumentsHandlerUtils.checkNotNull(quality, "quality");
            ArgumentsHandlerUtils.checkNotNull(value, "value");
            ArgumentsHandlerUtils.checkNotNull(timestamp, "timestamp");

            String[] split = String.valueOf(timestamp).split("\\.");
            String days = split[0];
            String seconds = split[1];
            Long oleTime = ArgumentsHandlerUtils.transferOleTimeToMilliSeconds(days + "." + seconds);
            // 构建Point
            Point point = Point.measurement(tagName)
                    .addTag("quality", String.valueOf(quality))
                    .addField("value", value)
                    .time(oleTime, WritePrecision.MS);
            log.debug("data that needs to be written to influxdb: {}", point.toLineProtocol());
            points.add(point);
            writeApi.writePoints(points);
        }
        return points.size();
    }

    /**
     * 查询指定测点的数据总数
     *
     * @param tagName 测点名
     */
    public static long queryAtRtDbTagItemPointsCounts(String tagName) {
        String flux = "from(bucket: \"xisun_influx_bucket\")\n" +
                " |> range(start: 2022-03-16T08:00:00Z, stop: 2028-04-30T08:00:00Z)\n" +
                " |> filter(fn: (r) => r[\"_measurement\"] == \"" + tagName + "\")" +
                " |> count(column: \"_value\")";

        long[] count = {0};
        QUERY_API.query(flux, (cancellable, fluxRecord) -> {
                    // 测点名，测点的tag值，以及对应的数据总数
                    log.debug("query atrtdb tag name: {}, tag value: {}, count: {}", fluxRecord.getMeasurement(),
                            fluxRecord.getValueByKey("quality"), fluxRecord.getValue());
                    Object currentCount = fluxRecord.getValue();
                    ArgumentsHandlerUtils.checkNotNull(currentCount, "currentCount");
                    count[0] = count[0] + (long) currentCount;
                },
                Throwable::printStackTrace,
                () -> log.debug("the query complete"));
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return count[0];
    }

    /**
     * 查询指定测点的实时数据（即最新的一条数据）
     *
     * @param tagName 测点名
     * @return 查询结果
     */
    public static ATRTDBDataItem queryRealTimeData(String tagName) {
        ArgumentsHandlerUtils.checkNotNull(tagName, "tagName");

        String flux = "from(bucket: \"xisun_influx_bucket\")\n" +
                " |> range(start: -10d, stop: 10d)\n" +
                " |> filter(fn: (r) => r[\"_measurement\"] == \"" + tagName + "\")" +
                " |> last(column: \"_value\")";

        ATRTDBDataItem atrtdbDataItem = new ATRTDBDataItem();
        QUERY_API.query(flux, (cancellable, fluxRecord) -> {
                    String measurement = fluxRecord.getMeasurement();
                    Object quality = fluxRecord.getValueByKey("quality");
                    Object value = fluxRecord.getValue();
                    Instant time = fluxRecord.getTime();
                    ArgumentsHandlerUtils.checkNotNull(measurement, "query result of measurement");
                    ArgumentsHandlerUtils.checkNotNull(quality, "query result of quality");
                    ArgumentsHandlerUtils.checkNotNull(value, "query result of value");
                    ArgumentsHandlerUtils.checkNotNull(time, "query result of time");
                    atrtdbDataItem.setTagName(measurement);
                    atrtdbDataItem.setQuality(Short.parseShort((String) quality));
                    atrtdbDataItem.setValue((double) value);
                    atrtdbDataItem.setTimestamp(ArgumentsHandlerUtils.transferMilliSecondsToOleTime(time.toEpochMilli()));
                },
                Throwable::printStackTrace,
                () -> log.debug("the query complete"));
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return atrtdbDataItem;
    }

    /**
     * 查询历史数据
     *
     * @param atrtdbQueryParam 查询参数
     * @return 查询结果
     */
    public static Map<String, List<ATRTDBDataItem>> queryHistoryData(ATRTDBQueryParam atrtdbQueryParam) {
        List<String> tagNames = atrtdbQueryParam.getTagNames();
        ATRTDBHDQueryType type = atrtdbQueryParam.getType();
        String startTime = ArgumentsHandlerUtils.transferLocalTimeToUtcTime(atrtdbQueryParam.getStartTime());
        String endTime = ArgumentsHandlerUtils.transferLocalTimeToUtcTime(atrtdbQueryParam.getEndTime());
        Map<String, List<ATRTDBDataItem>> results = new HashMap<>(tagNames.size());
        for (String tagName : tagNames) {
            String flux = "from(bucket: \"xisun_influx_bucket\")\n" +
                    " |> range(start: " + startTime + ", stop: " + endTime + ")\n" +
                    " |> filter(fn: (r) => r[\"_measurement\"] == \"" + tagName + "\")";
            /*if (type.equals(ATRTDBHDQueryType.RawByTime)) {
                flux = flux + " |> group(columns: [\"_time\"])";
            }*/
            List<ATRTDBDataItem> atrtdbDataItems = new ArrayList<>();
            QUERY_API.query(flux, (cancellable, fluxRecord) -> {
                        ATRTDBDataItem atrtdbDataItem = new ATRTDBDataItem();
                        String measurement = fluxRecord.getMeasurement();
                        Object quality = fluxRecord.getValueByKey("quality");
                        Object value = fluxRecord.getValue();
                        Instant time = fluxRecord.getTime();
                        ArgumentsHandlerUtils.checkNotNull(measurement, "query result of measurement");
                        ArgumentsHandlerUtils.checkNotNull(quality, "query result of quality");
                        ArgumentsHandlerUtils.checkNotNull(value, "query result of value");
                        ArgumentsHandlerUtils.checkNotNull(time, "query result of time");
                        atrtdbDataItem.setTagName(measurement);
                        atrtdbDataItem.setQuality(Short.parseShort((String) quality));
                        atrtdbDataItem.setValue((double) value);
                        atrtdbDataItem.setTimestamp(ArgumentsHandlerUtils.transferMilliSecondsToOleTime(time.toEpochMilli()));
                        atrtdbDataItems.add(atrtdbDataItem);
                    },
                    Throwable::printStackTrace,
                    () -> log.debug("the query complete"));
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            results.put(tagName, atrtdbDataItems);
        }
        return results;
    }
}

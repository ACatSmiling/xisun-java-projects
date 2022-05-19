package cn.xisun.influxdb.service;

import cn.xisun.influxdb.bean.historyData.ATRTDBHDQueryType;
import cn.xisun.influxdb.bean.historyData.ATRTDBQueryParam;
import cn.xisun.influxdb.bean.realTimeData.ATRTDBDataItem;
import cn.xisun.influxdb.utils.ArgumentsHandlerUtils;
import cn.xisun.influxdb.utils.InfluxDBClientUtil;
import com.influxdb.client.*;
import io.reactivex.BackpressureOverflowStrategy;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/2 9:52
 * @description InfluxDBAntaiService测试类
 */
@Slf4j
class InfluxDBServiceAntaiTest {

    @Test
    @Deprecated
    void testCreateMeasurementPointsAtFirst() {
        InfluxDBAntaiService.createMeasurementPointsAtFirst();
    }

    @Test
    void testCreateAtRtDbTagItem() {
        InfluxDBAntaiService.createAtRtDbTagItem();
    }

    @Test
    void testQueryAtRtDbTagItemCounts() {
        int count = InfluxDBAntaiService.queryAtRtDbTagItemCounts();
        log.info("the total number of atrtdb tag item is: {}", count);
    }

    @Test
    void t() {
        // OLE时间：用double类型表示，整数部分是自1900年至今流逝的天数包含润年计算，而小数部是不足一天流逝的总秒数
        // 1899-12-30 24:00:00的毫秒数，等同于1900-01-01 00:00:00
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1899, Calendar.DECEMBER, 30, 0, 0, 0);
        long start = calendar.getTimeInMillis();
        System.out.println("===========================");

        // 2017-09-05 15:16:07的毫秒数
        Calendar calendar2 = Calendar.getInstance();
        calendar2.clear();
        calendar2.set(2017, Calendar.SEPTEMBER, 5, 15, 16, 7);
        long now2 = calendar2.getTimeInMillis();
        // 整天数
        long days2 = (now2 - start) / (1000 * 60 * 60 * 24);
        // 去除整天数后的剩余秒数
        long seconds2 = (now2 - start) / 1000 - days2 * 24 * 60 * 60;
        System.out.println(days2);// 42983
        System.out.println(seconds2);// 54967
        System.out.println("===========================");

        // 当前时间的毫秒数
        long now = Instant.now().toEpochMilli();
        // 整天数
        long days = (now - start) / (1000 * 60 * 60 * 24);
        // 去除整天数后的剩余秒数
        long seconds = (now - start) / 1000 - days * 24 * 60 * 60;
        System.out.println(days);// 44675
        System.out.println(seconds);// 74510
        System.out.println(Double.parseDouble(days + "." + seconds));// 44675.7451
        System.out.println("===========================");


        double timestamp = Double.parseDouble(days + "." + 74511L);
        String s = String.valueOf(timestamp);
        System.out.println(s);
        String[] split = s.split("\\.");
        System.out.println(split[0]);
        System.out.println(split[1]);
    }

    @Test
    void tt() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        long time = Calendar.getInstance().getTimeInMillis();
        System.out.println("当前日期：" + sdf.format(time));

        long now = Instant.now().toEpochMilli();
        System.out.println(now);
        System.out.println(sdf.format(now));

        long days = 44674L;
        long seconds = 55950L;
        System.out.println("转换之前毫秒数：" + (days * 24 * 60 * 60 * 1000 + seconds * 1000));
        System.out.println("转换之前日期：" + sdf.format(days * 24 * 60 * 60 * 1000 + seconds * 1000));
        Long oleTime = ArgumentsHandlerUtils.transferOleTimeToMilliSeconds(days + "." + seconds);
        System.out.println("转换之后毫秒数：" + oleTime);
        System.out.println("转换之后日期：" + sdf.format(oleTime));

        long second2 = 70001L;
        long second3 = 700010L;
        System.out.println(String.valueOf(second2).length());
        System.out.println(String.valueOf(second3).length());
    }

    @Test
    void ttt() {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1899, Calendar.DECEMBER, 30, 0, 0, 0);
        long start = calendar.getTimeInMillis();

        long time1 = 1650884967530L;
        long days1 = (time1 - start) / (1000 * 60 * 60 * 24);
        long seconds1 = (time1 - start) / 1000 - days1 * 24 * 60 * 60;

        long time2 = 1650884968544L;
        long days2 = (time2 - start) / (1000 * 60 * 60 * 24);
        long seconds2 = (time2 - start) / 1000 - days2 * 24 * 60 * 60;

        Long oleTime1 = ArgumentsHandlerUtils.transferOleTimeToMilliSeconds(days1 + "." + seconds1);
        System.out.println(oleTime1);
        Long oleTime2 = ArgumentsHandlerUtils.transferOleTimeToMilliSeconds(days2 + "." + seconds2);
        System.out.println(oleTime2);
    }

    // JNWLC0601D00101M：DoubleFloat
    // JX3-1F1-JSJ：SwitchValue
    // SDTSG-0HB：VariableString
    // HFSDJZX-NEW-01-04-03：DoubleInteger
    @Test
    void testWriteRealTimeData() {
        // 1 获取客户端
        InfluxDBClient influxDBClient = InfluxDBClientUtil.getInfluxDBClient();
        WriteApi writeApi = influxDBClient.makeWriteApi(WriteOptions.builder()
                .batchSize(5000)
                .flushInterval(1000)
                .backpressureStrategy(BackpressureOverflowStrategy.DROP_OLDEST)
                .bufferLimit(10000)
                .jitterInterval(1000)
                .retryInterval(5000)
                .build());

        // 2 业务处理
        String[] tagNameAll = {"JNWLC0601D00101M", "JX3-1F1-JSJ"};
        DecimalFormat df = new DecimalFormat("0.00");
        for (String tagName : tagNameAll) {
            ArrayList<ATRTDBDataItem> atrtdbDataItems = new ArrayList<>(2000);
            // 测点索引后缀
            int addition = 101;
            for (int i = 0; i < 10; i++) {
                // 每一个测点写入1000条数据
                for (int j = 0; j < 1000; j++) {
                    // 实时数据，普通数据项
                    ATRTDBDataItem atrtdbDataItem = new ATRTDBDataItem();
                    // 测点名
                    atrtdbDataItem.setTagName(tagName + "-" + addition);
                    // 测点质量，暂不明确其具体逻辑，统一定义为0
                    atrtdbDataItem.setQuality((short) 0);
                    // 测点值，随机的一个Double值，范围为[10, 70)
                    atrtdbDataItem.setValue(10 + ThreadLocalRandom.current().nextDouble(60));
                    // 测点时间戳，由当前时间的毫秒数转换而来
                    long now = Instant.now().toEpochMilli();
                    atrtdbDataItem.setTimestamp(ArgumentsHandlerUtils.transferMilliSecondsToOleTime(now));
                    atrtdbDataItems.add(atrtdbDataItem);
                    try {
                        // 考虑到转换过程中精度的问题，每条数据时间戳间隔应设置大点，否则后续转换可能会导致两条数据时间戳一样
                        // 此处设置为1s（也出现不同数据时间戳相同的情况），在实际应用时，数据采集的时间间隔应该是比这个数大
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Integer count = InfluxDBAntaiService.writeRealTimeData(writeApi, atrtdbDataItems);
                log.info("write data successful, total number: {}", count);
                atrtdbDataItems.clear();
                addition++;
            }
        }

        // 3 关闭客户端
        writeApi.close();
        influxDBClient.close();
    }

    @Test
    void testQueryAtRtDbTagItemPointsCounts() {
        String[] tagNameAll = {"JNWLC0601D00101M", "JX3-1F1-JSJ", "SDTSG-0HB", "HFSDJZX-NEW-01-04-03"};
        for (String tagName : tagNameAll) {
            int addition = 101;
            for (int i = 0; i < 10; i++) {
                String queryTagName = tagName + "-" + addition;
                long count = InfluxDBAntaiService.queryAtRtDbTagItemPointsCounts(queryTagName);
                log.info("the total number of {} tag item is: {}", tagName, count);
                addition++;
            }
        }
    }

    @Test
    void testQueryRealTimeData() {
        String[] tagNameAll = {"JNWLC0601D00101M", "JX3-1F1-JSJ"};
        for (String tagName : tagNameAll) {
            int addition = 101;
            for (int i = 0; i < 10; i++) {
                String queryTagName = tagName + "-" + addition;
                ATRTDBDataItem atrtdbDataItem = InfluxDBAntaiService.queryRealTimeData(queryTagName);
                log.info("query tag name: {}, result: {}", queryTagName, atrtdbDataItem);
                addition++;
            }
        }
    }

    @Test
    void testQueryHistoryData() {
        ATRTDBQueryParam atrtdbQueryParam = new ATRTDBQueryParam();
        List<String> tagNames = new ArrayList<>();
        tagNames.add("JNWLC0601D00101M-101");
        tagNames.add("JX3-1F1-JSJ-110");
        atrtdbQueryParam.setTagNames(tagNames);
        // 根据时间排序，查询某一时间段的历史数据
        atrtdbQueryParam.setType(ATRTDBHDQueryType.RawByTime);
        atrtdbQueryParam.setStartTime("2022-04-26 12:00:00");
        atrtdbQueryParam.setEndTime("2022-04-26 12:20:00");
        Map<String, List<ATRTDBDataItem>> results = InfluxDBAntaiService.queryHistoryData(atrtdbQueryParam);
        Set<Map.Entry<String, List<ATRTDBDataItem>>> entries = results.entrySet();
        for (Map.Entry<String, List<ATRTDBDataItem>> entry : entries) {
            List<ATRTDBDataItem> items = entry.getValue();
            for (ATRTDBDataItem item : items) {
                log.debug("current result: {}", item);
            }
            log.info("sort query type is: {}, the total number of {} tag item is: {}",
                    ATRTDBHDQueryType.RawByTime, entry.getKey(), entry.getValue().size());
        }
    }
}
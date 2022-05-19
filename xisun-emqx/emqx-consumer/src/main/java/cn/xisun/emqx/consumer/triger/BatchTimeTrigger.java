package cn.xisun.emqx.consumer.triger;

import cn.xisun.emqx.consumer.bean.ATRTDBDataItem;
import cn.xisun.emqx.consumer.callback.MessageHandling;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/5/11 9:06
 * @description
 */
@Slf4j
public class BatchTimeTrigger {
    /**
     * 连接安泰时序数据库操作，如果此步骤阻塞，则BatchTimeTrigger类后续无法正常加载
     */
    /*private static final ATRTDBAPIPrx M_PROXY = AtrtDbApiPrxUtil.getAtrtDbApiPrx();*/

    private static final Lock LOCK = new ReentrantLock();

    private static long start = Instant.now().toEpochMilli();

    /**
     * 时间触发器
     */
    public static void timeTrigger() {
        // noinspection InfiniteLoopStatement
        while (true) {
            // 40s触发一次
            try {
                TimeUnit.SECONDS.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long now = Instant.now().toEpochMilli();
            log.debug("now: {}, start: {}, (now - start) is: {}", now, start, (now - start));
            // 排除时间触发器触发时，批数触发器处于触发状态的情况
            if ((now - start) >= (40 * 1000)) {
                start = Instant.now().toEpochMilli();
                log.debug("the time trigger starts working, current start: {}", start);
                exec();
                log.debug("the current time trigger ends");
            }
        }
    }

    /**
     * 批数触发器
     */
    public static void batchTrigger() {
        // 批数触发器触发时，重置时间触发器起始时间
        start = Instant.now().toEpochMilli();
        log.debug("the batch trigger starts working, current start: {}", start);
        exec();
        log.debug("the current batch trigger ends");
    }

    /**
     * 数据处理
     */
    public static void exec() {
        ATRTDBDataItem[] atrtdbDataItems = null;
        LOCK.lock();
        try {
            int size = MessageHandling.ATRTDB_TAG_ITEMS.size();
            log.debug("current atrtdb tag items size: {}", size);
            if (size != 0) {
                log.debug("start writing data to the database");
                atrtdbDataItems = new ATRTDBDataItem[size];
                atrtdbDataItems = MessageHandling.ATRTDB_TAG_ITEMS.toArray(atrtdbDataItems);
                MessageHandling.ATRTDB_TAG_ITEMS.clear();
                log.debug("end writing data to the database");
            }
        } finally {
            LOCK.unlock();
        }
        if (atrtdbDataItems != null) {
            log.info("all of the data: {}", Arrays.toString(atrtdbDataItems));
            // 写数据到安泰时序数据库
            /*ATRTDBErrorListHolder errors = new ATRTDBErrorListHolder();
            int count = M_PROXY.RTDWriteEx(atrtdbDataItems, errors);
            log.info("number of write successes: {}, errors: {}", count, Arrays.toString(errors.value));*/

            // 模拟数据库写入延迟
            try {
                TimeUnit.MINUTES.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

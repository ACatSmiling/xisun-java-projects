package cn.xisun.emqx.producer.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * @author XiSun
 * @version 1.0
 * @date 2022/4/23 14:24
 * @description 参数工具类
 */
public class ArgumentsHandlerUtil {
    /**
     * 检查对象不为Null
     *
     * @param obj  检查对象
     * @param name 对象名
     * @throws NullPointerException 检查对象若为Null，返回空指针异常
     */
    public static void checkNotNull(final Object obj, final String name) throws NullPointerException {
        Objects.requireNonNull(obj, () -> "Expecting a not null reference for " + name);
    }

    /**
     * 转换OLE格式时间为毫秒数
     *
     * @param oleTime OLE格式时间
     * @return 毫秒时间
     */
    public static Long transferOleTimeToMilliSeconds(Double oleTime) {
        return Double.valueOf(((oleTime - 25569) * 86400.0 - 8 * 3600 + 0.5) * 1000).longValue();
    }

    /**
     * 转换毫秒时间为OLE格式时间
     *
     * @param time 毫秒时间
     * @return OLE格式时间
     */
    public static double transferMilliSecondsToOleTime(long time) {
        time = time / 1000;
        return (25569 + time / 86400.0 + 8.0 / 24.0);
    }

    /**
     * 转换本地时间为UTC时间，InfluxDB的time使用的是UTC时间格式，相比北京时间会有8小时的差距
     * 本地时间格式：yyyy-MM-dd HH:mm:ss，如：2022-04-26 12:00:00
     * UTC时间格式：yyyy-MM-dd T HH:mm:ss Z，如：2022-04-26T12:00:00Z
     *
     * @param localTime 本地时间
     * @return UTC时间
     */
    public static String transferLocalTimeToUtcTime(String localTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat dtfUtc = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date localDate = sdf.parse(localTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(localDate);
            // 本地时间减8小时
            calendar.add(Calendar.HOUR, -8);
            Date utcDate = calendar.getTime();
            return dtfUtc.format(utcDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String s = "2022-04-26 12:00:00";
        String s1 = transferLocalTimeToUtcTime(s);
        System.out.println(s1);

        long l = System.currentTimeMillis();

        double v = transferMilliSecondsToOleTime(l);
        System.out.println(v);

        Long aLong = transferOleTimeToMilliSeconds(44686.7);
        System.out.println(aLong);
    }
}

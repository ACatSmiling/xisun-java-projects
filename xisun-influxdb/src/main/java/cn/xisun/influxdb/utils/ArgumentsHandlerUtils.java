package cn.xisun.influxdb.utils;

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
public class ArgumentsHandlerUtils {
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
     * OLE时间是用double类型来表示的，整数部分是自1900年至今流逝的天数包含润年计算，而小数部是不足一天流逝的总秒数
     * <p>
     * 将毫秒数转换为OLE格式的Double数时，可能存在丢失小数点最后面0的问题
     * 比如：天数为42983，秒数为75040，通过Double.parseDouble(days + "." + seconds)得到的是42983.7504
     * 因此，本方法是将上述问题丢失的0找回来，然后再转换为毫秒数
     * 注意，Java本地时间是从1970-01-01 00:00:00开始计算的，要把1900年至1970年中间的这70年去掉
     *
     * @param oleTime OLE时间格式的字符串
     */
    public static Long transferOleTimeToMilliSeconds(String oleTime) {
        // 1900-01-01 00:00:00
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1900, Calendar.JANUARY, 1, 0, 0, 0);
        // 这是一个负值
        long start = calendar.getTimeInMillis();

        String[] split = oleTime.split("\\.");
        String day = split[0];
        String second = split[1];
        if (second.length() > 5) {
            throw new RuntimeException(second + " maybe too long, please check.");
        }
        if (second.length() < 5) {
            StringBuilder sb = new StringBuilder(5);
            char[] chars = second.toCharArray();
            // OLE格式时间小数点后为不满一天的秒数，其值不会超过24 * 60 * 60 = 86400
            for (int i = 0; i < 5; i++) {
                if (i >= chars.length) {
                    sb.append(0);
                } else {
                    sb.append(chars[i]);
                }
            }
            System.out.println(sb);
            return (Long.parseLong(day) * 24 * 60 * 60 + Long.parseLong(sb.toString())) * 1000 + start;
        }
        return (Long.parseLong(day) * 24 * 60 * 60 + Long.parseLong(second)) * 1000 + start;
    }

    /**
     * 转换毫秒时间为OLE格式时间
     *
     * @param time 毫秒时间
     * @return OLE格式时间
     */
    public static double transferMilliSecondsToOleTime(long time) {
        // 1900-01-01 00:00:00的毫秒数，等同于1900-01-01 00:00:00
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1900, Calendar.JANUARY, 1, 0, 0, 0);
        long start = calendar.getTimeInMillis();

        // 整天数
        long days = (time - start) / (1000 * 60 * 60 * 24);
        // 去除整天数后的剩余秒数
        long seconds = (time - start) / 1000 - days * 24 * 60 * 60;
        return Double.parseDouble(days + "." + seconds);
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
    }
}

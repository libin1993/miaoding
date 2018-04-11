package cn.cloudworkshop.miaoding.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Author：Libin on 2016/12/19 10:11
 * Email：1993911441@qq.com
 * Describe：时间戳
 */
public class DateUtils {

    /**
     * @param type
     * @param time
     * @return 获取日期
     */
    public static String getDate(String type, int time) {
        SimpleDateFormat sdf = new SimpleDateFormat(type, Locale.CHINA);
        return sdf.format(new Date(time * 1000L));
    }

    /**
     * @param type
     * @param time
     * @return 毫秒
     */
    public static long getMillisecond(String type, String time) {
        SimpleDateFormat sdf = new SimpleDateFormat(type, Locale.CHINA);
        long seconds = 0;
        try {
            seconds = sdf.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return seconds;
    }

    /**
     * @return 当前时间，精确都秒
     */
    public static long getCurrentTime() {
        return System.currentTimeMillis() / 1000;
    }
}

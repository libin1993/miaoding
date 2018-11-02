package cn.cloudworkshop.miaoding.utils;

import java.math.BigDecimal;

/**
 * Author：Libin on 2018/8/8 09:00
 * Email：1993911441@qq.com
 * Describe：
 */
public class BigDecimalUtils {

    /**
     * @param value1
     * @param value2
     * @return  加
     */
    public static double add(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        return b1.add(b2).doubleValue();
    }

    /**
     * @param v1
     * @param v2
     * @return 减
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * @param v1
     * @param v2
     * @return  乘
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * @param v1
     * @param v2
     * @return  除
     */
    public static double div(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

}

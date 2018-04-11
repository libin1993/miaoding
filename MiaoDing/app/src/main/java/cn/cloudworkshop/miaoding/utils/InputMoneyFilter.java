package cn.cloudworkshop.miaoding.utils;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author：Libin on 2018/3/14 11:21
 * Email：1993911441@qq.com
 * Describe：输入金额
 */
public class InputMoneyFilter implements InputFilter {

    //最大金额
    private static final int MAX_VALUE = 1000000;

    /**
     * @param source 新输入的字符串
     * @param start  新输入的字符串起始下标，一般为0
     * @param end    新输入的字符串终点下标，一般为source长度-1
     * @param dest   输入之前文本框内容
     * @param dstart 原内容起始坐标，一般为0
     * @param dend   原内容终点坐标，一般为dest长度-1
     * @return
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        String sourceText = source.toString();
        String destText = dest.toString();
        //验证删除等按键
        if (TextUtils.isEmpty(sourceText)) {
            if (dstart == 0 && destText.indexOf(".") == 1) {//保证小数点不在第一个位置
                return "0";
            }
            return "";
        }
        Pattern pattern = Pattern.compile("([0-9]|\\.)*");
        Matcher matcher = pattern.matcher(source);
        //已经输入小数点的情况下，只能输入数字
        if (destText.contains(".")) {
            if (!matcher.matches()) {
                return "";
            } else {
                if (".".equals(source)) { //只能输入一个小数点
                    return "";
                }
            }
            //验证小数点精度，保证小数点后只能输入两位
            int index = destText.indexOf(".");
            int length = destText.trim().length() - index;
            if (length > 2 && dstart > index) {
                return "";
            }
        } else {
            //没有输入小数点的情况下，只能输入小数点和数字，但首位不能输入小数点
            if (!matcher.matches()) {
                return "";
            } else {
                if ((".".equals(source)) && dstart == 0) {//第一个位置输入小数点的情况
                    return "0.";
                }
            }
        }
        String first = destText.substring(0, dstart);

        String second = destText.substring(dstart, destText.length());

        String sum = first + sourceText + second;
        //验证输入金额的大小
        double sumText = Double.parseDouble(sum);
        //这里得到输入完之后需要计算的金额  如果这个金额大于了事先设定的金额,那么就直接返回  不需要加入输入的字符
        if (sumText > MAX_VALUE) {
            return dest.subSequence(dstart, dend);
        }
        //如果输入的金额小于事先规定的金额
        return dest.subSequence(dstart, dend) + sourceText;
    }
}

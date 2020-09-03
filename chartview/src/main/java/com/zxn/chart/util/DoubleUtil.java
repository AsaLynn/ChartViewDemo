package com.zxn.chart.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class DoubleUtil {

    /**
     * 保留两位小数点
     *
     * @param d double
     * @return String
     */
    public static String formetDouble(double d) {
        return new DecimalFormat("#####0.00").format(d);
    }

    /**
     * 保留两位小数点
     *
     * @param money money
     * @return String
     */
    public static String formetDouble(String money) {
        DecimalFormat df = new DecimalFormat("#####0.00");
        BigDecimal bigDecimal = new BigDecimal(money);
        return df.format(bigDecimal);
    }

    /**
     * 保留两位小数点
     *
     * @param str str
     * @return boolean
     */
    public static boolean check2Double(String str) {
        if (str.contains(".") && str.indexOf(".") + 3 < str.length()) {
            return false;
        } else
            return true;
    }
}
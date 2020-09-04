package com.zxn.chartview;

import androidx.annotation.ColorInt;

/**
 * Created by zxn on 2020/9/4.
 */
public interface IChartValueEntity {

    /**
     * 柱状图的类型
     *
     * @return 类型名称
     */
    String valueType();

    /**
     * 不同柱状图的颜色
     *
     * @return 16进制的颜色值
     */
    @ColorInt
    int valueColor();
}

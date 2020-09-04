package com.chart_demo1;

import androidx.annotation.ColorInt;

import com.zxn.chartview.IChartValueEntity;

/**
 * Created by zxn on 2020/9/4.
 */
public class ChartValueEntity implements IChartValueEntity {
    public String valueType;
    public @ColorInt int valueColor;

    public ChartValueEntity(String valueType,  @ColorInt int valueColor) {
        this.valueType = valueType;
        this.valueColor = valueColor;
    }

    @Override
    public String valueType() {
        return valueType;
    }

    @Override
    public int valueColor() {
        return valueColor;
    }
}

package com.chart_demo1;

import com.zxn.chart.IChartEntity;

/**
 * Created by zxn on 2020/9/3.
 */
public class ChartEntity implements IChartEntity {
    public String name;
    public double value;

    public ChartEntity(String name, double value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String chartName() {
        return name;
    }

    @Override
    public double getValue() {
        return value;
    }
}

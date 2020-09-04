package com.chart_demo1;


import com.zxn.chartview.IChartEntity;

/**
 * Created by zxn on 2020/9/3.
 */
public class ChartEntity implements IChartEntity {
    public String name;
    public double value;
    public double secondValue;

    public ChartEntity(String name, double value, double secondValue) {
        this.name = name;
        this.value = value;
        this.secondValue = secondValue;
    }

    public ChartEntity(String name, double value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String chartName() {
        return null;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public double[] getValues() {
        double[] doubles = new double[2];
        doubles[0] = value;
        doubles[1] = secondValue;
        return doubles;
    }
}

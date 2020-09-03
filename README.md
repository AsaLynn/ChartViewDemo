# ChartViewDemo
简单图表
- 柱状图

# 效果图
![Image text](/image/image01.png)


# 依赖
```
implementation 'com.zxn.chartview:chartview:1.0.0'
```
# xml布局
```
<com.zxn.chart.BarChartView
    android:layout_margin="10dp"
    android:id="@+id/columnChartView"
    android:layout_width="match_parent"
    android:layout_height="320dp"
    android:background="#ffffff" />
```

# 代码
```
List<IChartEntity> list = new ArrayList<>();
list.add(new ChartEntity("top1", 1000.00));
list.add(new ChartEntity("top2", 5000.00));
list.add(new ChartEntity("top3", 1820.00));
list.add(new ChartEntity("top4", 1130.00));
list.add(new ChartEntity("top5", 1253.00));
columnChartView.setList(list);
```

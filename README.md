# ChartViewDemo

# 简单图表
- 柱状图

# 效果图
![Image text](https://github.com/zhang721688/ChartViewDemo/blob/master/image/image01.png)

# 依赖
```
implementation 'com.zxn.chartview:chartview:1.0.1'
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
# 更新日志
- chartview:1.0.1
```
git tag -a v1.0.1 -m '发布正式使用'
git push origin v1.0.1
git tag
```
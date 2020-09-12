# ChartViewDemo

# 简单图表
- 柱状图

# 效果图
![Image text](https://github.com/zhang721688/ChartViewDemo/blob/master/image/image01.png)
![Image text](https://github.com/zhang721688/ChartViewDemo/blob/master/image/image02.png)
![Image text](https://github.com/zhang721688/ChartViewDemo/blob/master/image/image03.png)

# 依赖
```
implementation 'com.zxn.chartview:chartview:1.0.3'
```
# 柱状图
## xml布局
```
<com.zxn.chartview.BarChartView
    android:layout_margin="10dp"
    android:id="@+id/columnChartView"
    android:layout_width="match_parent"
    android:layout_height="320dp"
    android:background="#ffffff" />
```

## 代码
```
List<IChartEntity> list = new ArrayList<>();
list.add(new ChartEntity("top1", 1000.00));
list.add(new ChartEntity("top2", 5000.00));
list.add(new ChartEntity("top3", 1820.00));
list.add(new ChartEntity("top4", 1130.00));
list.add(new ChartEntity("top5", 1253.00));
columnChartView.setList(list);
```
# 环形图
## xml布局
```
<com.zxn.chartview.Ringchartview
    android:id="@+id/fitchart"
    android:layout_width="250dp"
    android:layout_height="250dp"
    android:layout_gravity="center"
    android:layout_margintop="50dp"
    app:animationmode="overdraw"
    app:backstrokecolor="@color/bg_cccccc"
    app:titlesize="18sp"
    app:valuestrokecolor="@color/bg_50a3f7" />
```
## 代码使用
```
fitChart.setMaxValue(100);
fitChart.setValue(80);
```

# 更新日志
- chartview:1.0.3
```
git tag -a v1.0.3 -m '增加环形比例'
git push origin v1.0.3
git tag
```
- chartview:1.0.2
```
git tag -a v1.0.2 -m '最低支持Android4.0'
git push origin v1.0.2
git tag
```
- chartview:1.0.1
```
git tag -a v1.0.1 -m '发布正式使用'
git push origin v1.0.1
git tag
```

package com.chart_demo1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.zxn.chartview.BarChartView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BarChatActivity extends AppCompatActivity {

    @BindView(R.id.bar_chart_view)
    BarChartView barChartView;

    public static void jumpTo(Context context) {
        Intent intent = new Intent(context, BarChatActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chat);
        ButterKnife.bind(this);

        List<ChartEntity> list = new ArrayList<>();
        list.add(new ChartEntity("top1", 1000.00, 200.00));
        list.add(new ChartEntity("top2", 5000.00, 300.00));
        list.add(new ChartEntity("top3", 1820.00, 400.00));
        list.add(new ChartEntity("top4", 1130.00, 600.00));
        list.add(new ChartEntity("top5", 1253.00, 800.00));
        List<ChartValueEntity> valueList = new ArrayList<>();
        valueList.add(new ChartValueEntity("交易金额", Color.parseColor("#02BB9D")));
        valueList.add(new ChartValueEntity("储值金额", Color.parseColor("#33C758")));
        barChartView.setChartValueList(valueList);
        barChartView.setList(list);
    }
}
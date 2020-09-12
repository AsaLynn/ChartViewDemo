package com.chart_demo1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.zxn.chartview.fitchart.RingChartView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zxn on 2020/9/12.
 */
public class CircleChatActivity extends AppCompatActivity {

    @BindView(R.id.fitChart)
    RingChartView fitChart;


    public static void jumpTo(Context context) {
        Intent intent = new Intent(context, CircleChatActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_chat);
        ButterKnife.bind(this);

        fitChart.setMaxValue(100);
        fitChart.setValue(80);
    }

    @OnClick(R.id.read_tv)
    public void onViewClicked() {
        fitChart.setMaxValue(100);
        fitChart.setValue(80);
    }
}
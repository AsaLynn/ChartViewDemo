package com.chart_demo1;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class EnterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_bar1, R.id.tv_bar2, R.id.tv_bar3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_bar1:
                MainActivity.jumpTo(this);
                break;
            case R.id.tv_bar2:
                VerticalColumnarGraphViewActivity.jumpTo(this);
                break;
            case R.id.tv_bar3:
                BarChatActivity.jumpTo(this);
                break;
            case R.id.tv_bar4:
                //BarChatActivity.jumpTo(this);

                break;
        }
    }
}
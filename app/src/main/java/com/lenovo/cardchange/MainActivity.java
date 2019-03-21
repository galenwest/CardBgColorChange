package com.lenovo.cardchange;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lenovo.cardchange.activity.CardChangeBgColorActivity;
import com.lenovo.cardchange.activity.CardExtractColorActivity;
import com.lenovo.cardchange.activity.ExtractColorActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt1 = findViewById(R.id.bt1);
        Button bt2 = findViewById(R.id.bt2);
        Button bt3 = findViewById(R.id.bt3);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 背景渐变切换
                startActivity(new Intent(MainActivity.this, CardChangeBgColorActivity.class));
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 颜色提取测试
                startActivity(new Intent(MainActivity.this, ExtractColorActivity.class));
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 颜色取色切换
                startActivity(new Intent(MainActivity.this, CardExtractColorActivity.class));
            }
        });
    }
}

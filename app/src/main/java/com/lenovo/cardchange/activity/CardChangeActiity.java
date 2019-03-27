package com.lenovo.cardchange.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.bakerj.infinitecards.InfiniteCardView;
import com.lenovo.cardchange.R;
import com.lenovo.cardchange.adapter.ImageAdapter;

public class CardChangeActiity extends AppCompatActivity {
    InfiniteCardView mCardView;
    Button change_left, change_right;
    ImageAdapter adapter;
    private int[] resId = {R.mipmap.sailing_boat, R.mipmap.copenhagen, R.mipmap.easter, R.mipmap
            .landscape, R.mipmap.the_ioness};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_change_actiity);
        initView();
        initData();
    }

    private void initView() {
        mCardView = findViewById(R.id.cardView);
        change_left = findViewById(R.id.change_left);
        change_right = findViewById(R.id.change_right);
        adapter = new ImageAdapter(resId);
        mCardView.setAdapter(adapter);

        change_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCardView.setStyleLeft();
            }
        });
        change_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCardView.setStyleRight();
            }
        });
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CardChangeActiity.class);
        context.startActivity(intent);
    }

    private void initData() {
//        Intent intent = getIntent();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //将该Activity上的触碰事件交给GesturDetector处理
        mCardView.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

}

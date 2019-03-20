package com.lenovo.cardchange;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

    private LinearLayout background;
    // R G B颜色值
    private int r, g, b;
    // 模拟3个颜色
    private int color1 = Color.rgb(22, 98, 39);
    private int color2 = Color.rgb(222, 188, 132);
    private int color3 = Color.rgb(79, 28, 66);

    @Override
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        background = findViewById(R.id.background);
        ViewPager mViewPager = findViewById(R.id.viewPager);

        CardPagerAdapter mCardAdapter = new CardPagerAdapter();
        mCardAdapter.addCardItem(new CardItem(R.string.title_1, R.string.text_1));
        mCardAdapter.addCardItem(new CardItem(R.string.title_2, R.string.text_1));
        mCardAdapter.addCardItem(new CardItem(R.string.title_3, R.string.text_1));
        ShadowTransformer mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        mCardShadowTransformer.enableScaling(true);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
        // 滑动计算背景颜色
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 0:
                        r = getOffsetRed(color1, color2, positionOffset);
                        g = getOffsetGreen(color1, color2, positionOffset);
                        b = getOffsetBlue(color1, color2, positionOffset);
                        break;
                    case 1:
                        r = getOffsetRed(color2, color3, positionOffset);
                        g = getOffsetGreen(color2, color3, positionOffset);
                        b = getOffsetBlue(color2, color3, positionOffset);
                        break;
                    case 2:
                        r = getOffsetRed(color3, color2, positionOffset);
                        g = getOffsetGreen(color3, color2, positionOffset);
                        b = getOffsetBlue(color3, color2, positionOffset);
                        break;
                }
                background.setBackgroundColor(Color.rgb(r, g, b));
                setStatusBarColor(MainActivity.this, Color.rgb(r, g, b));
                // 计算灰度后比较
                if ((r * 299 + g * 587 + b * 114) > 127500) {
                    setAndroidNativeLightStatusBar(MainActivity.this, true);
                } else {
                    setAndroidNativeLightStatusBar(MainActivity.this, false);
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    // 设置状态栏颜色
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor(Activity activity, int statusColor) {
        Window window = activity.getWindow();
        //取消状态栏透明
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(statusColor);
        //设置系统状态栏处于可见状态
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
    }

    // 设置状态栏文字亮暗
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setAndroidNativeLightStatusBar(Activity activity, boolean dark) {
        View decor = activity.getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    // 计算两个颜色渐变中的红色值
    private int getOffsetRed(int color1, int color2, float positionOffset) {
        if (getRed(color1) > getRed(color2)) {
            return (int) (getRed(color1) - (getRed(color1) - getRed(color2)) * positionOffset);
        } else if (getRed(color1) < getRed(color2)) {
            return (int) (getRed(color1) + (getRed(color2) - getRed(color1)) * positionOffset);
        } else {
            return getRed(color1);
        }
    }

    // 计算两个颜色渐变中的绿色值
    private int getOffsetGreen(int color1, int color2, float positionOffset) {
        if (getGreen(color1) > getGreen(color2)) {
            return (int) (getGreen(color1) - (getGreen(color1) - getGreen(color2)) * positionOffset);
        } else if (getGreen(color1) < getGreen(color2)) {
            return (int) (getGreen(color1) + (getGreen(color2) - getGreen(color1)) * positionOffset);
        } else {
            return getGreen(color1);
        }
    }

    // 计算两个颜色渐变中的蓝色值
    private int getOffsetBlue(int color1, int color2, float positionOffset) {
        if (getBlue(color1) > getBlue(color2)) {
            return (int) (getBlue(color1) - (getBlue(color1) - getBlue(color2)) * positionOffset);
        } else if (getBlue(color1) < getBlue(color2)) {
            return (int) (getBlue(color1) + (getBlue(color2) - getBlue(color1)) * positionOffset);
        } else {
            return getBlue(color1);
        }
    }

    private int getRed(int color) {
        return (color & 0xff0000) >> 16;
    }

    private int getGreen(int color) {
        return (color & 0x00ff00) >> 8;
    }

    private int getBlue(int color) {
        return (color & 0x0000ff);
    }
}

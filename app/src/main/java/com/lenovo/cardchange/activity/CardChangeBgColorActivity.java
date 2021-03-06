package com.lenovo.cardchange.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.lenovo.cardchange.R;
import com.lenovo.cardchange.adapter.CardPagerAdapter;
import com.lenovo.cardchange.bean.CardItem;
import com.lenovo.cardchange.utils.ShadowTransformer;

public class CardChangeBgColorActivity extends AppCompatActivity {

    private LinearLayout background;
    // R G B颜色值
    private int r, g, b;
    // 模拟3个颜色
    private int color1 = Color.rgb(122, 58, 139);
    private int color2 = Color.rgb(222, 188, 132);
    private int color3 = Color.rgb(187, 235, 78);
    private int color4 = Color.rgb(79, 28, 66);

    @Override
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_change_bg_color);

        background = findViewById(R.id.background);
        ViewPager mViewPager = findViewById(R.id.viewPager);

        CardPagerAdapter mCardAdapter = new CardPagerAdapter(this);
        mCardAdapter.addCardItem(new CardItem(R.string.title_1, R.string.text_1, R.mipmap.crocus));
        mCardAdapter.addCardItem(new CardItem(R.string.title_2, R.string.text_1, R.mipmap.copenhagen));
        mCardAdapter.addCardItem(new CardItem(R.string.title_3, R.string.text_1, R.mipmap.reaching_spider));
        mCardAdapter.addCardItem(new CardItem(R.string.title_4, R.string.text_1, R.mipmap.cactus));
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
                        r = getOffsetRed(color3, color4, positionOffset);
                        g = getOffsetGreen(color3, color4, positionOffset);
                        b = getOffsetBlue(color3, color4, positionOffset);
                        break;
                    case 3:
                        r = getOffsetRed(color4, color3, positionOffset);
                        g = getOffsetGreen(color4, color3, positionOffset);
                        b = getOffsetBlue(color4, color3, positionOffset);
                        break;
                }
                // 设置渐变背景色
//                createLinearGradientBitmap(Color.rgb(r, g, b));
                // 设置纯背景色
                background.setBackgroundColor(Color.rgb(r, g, b));
                setStatusBarColor(CardChangeBgColorActivity.this, Color.rgb(r, g, b));
                // 计算灰度后比较
                if ((r * 299 + g * 587 + b * 114) > 127500) {
                    setAndroidNativeLightStatusBar(CardChangeBgColorActivity.this, true);
                } else {
                    setAndroidNativeLightStatusBar(CardChangeBgColorActivity.this, false);
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

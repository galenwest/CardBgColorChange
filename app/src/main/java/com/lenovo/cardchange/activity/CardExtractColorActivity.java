package com.lenovo.cardchange.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.lenovo.cardchange.R;
import com.lenovo.cardchange.adapter.ImagePagerAdapter;
import com.lenovo.cardchange.utils.ShadowTransformer;

@RequiresApi(api = Build.VERSION_CODES.M)
public class CardExtractColorActivity extends AppCompatActivity {

    LinearLayout background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_extract_color);

        background = findViewById(R.id.background);
        ViewPager mViewPager = findViewById(R.id.viewPager);

        ImagePagerAdapter mImageAdapter = new ImagePagerAdapter();
        mImageAdapter.addCardItem(R.mipmap.crocus);
        mImageAdapter.addCardItem(R.mipmap.copenhagen);
        mImageAdapter.addCardItem(R.mipmap.reaching_spider);
        mImageAdapter.addCardItem(R.mipmap.cactus);
        ShadowTransformer mCardShadowTransformer = new ShadowTransformer(mViewPager, mImageAdapter);
        mCardShadowTransformer.enableScaling(true);

        mViewPager.setAdapter(mImageAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);

        // 滑动计算背景颜色
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                extractColor(R.mipmap.crocus);
                            }
                        }).start();
                        break;
                    case 1:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                extractColor(R.mipmap.copenhagen);
                            }
                        }).start();
                        break;
                    case 2:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                extractColor(R.mipmap.reaching_spider);
                            }
                        }).start();
                        break;
                    case 3:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                extractColor(R.mipmap.cactus);
                            }
                        }).start();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                extractColor(R.mipmap.crocus);
            }
        }).start();
    }

    private void extractColor(int resource) {
        BitmapDrawable imgDraw = (BitmapDrawable) ContextCompat.getDrawable(CardExtractColorActivity.this, resource);
        assert imgDraw != null;
        Bitmap imgBitmap = imgDraw.getBitmap();
        Palette.from(imgBitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(final Palette palette) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int color, darkColor;
                        if (palette == null) return;
                        if (palette.getDarkVibrantColor(Color.TRANSPARENT) != Color.TRANSPARENT) {
                            color = palette.getVibrantColor(Color.TRANSPARENT);
                            darkColor = palette.getDarkVibrantColor(Color.TRANSPARENT);
                        } else if (palette.getDarkMutedColor(Color.TRANSPARENT) != Color.TRANSPARENT) {
                            color = palette.getMutedColor(Color.TRANSPARENT);
                            darkColor = palette.getDarkMutedColor(Color.TRANSPARENT);
                        } else {
                            color = palette.getLightVibrantColor(Color.TRANSPARENT);
                            darkColor = palette.getLightMutedColor(Color.TRANSPARENT);
                        }
                        background.setBackgroundColor(color);
                        createLinearGradientBitmap(color, darkColor);
//                        int r = getRed(color), g = getGreen(color), b = getBlue(color);
//                        setStatusBarColor(CardExtractColorActivity.this, Color.rgb(r, g, b));
//                        // 计算灰度后比较
//                        if ((r * 299 + g * 587 + b * 114) > 127500) {
//                            setAndroidNativeLightStatusBar(CardExtractColorActivity.this, true);
//                        } else {
//                            setAndroidNativeLightStatusBar(CardExtractColorActivity.this, false);
//                        }
                    }
                });
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

    private int getRed(int color) {
        return (color & 0xff0000) >> 16;
    }

    private int getGreen(int color) {
        return (color & 0x00ff00) >> 8;
    }

    private int getBlue(int color) {
        return (color & 0x0000ff);
    }


    //创建线性渐变背景色
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createLinearGradientBitmap(int color, int darkColor) {
        int bgColors[] = new int[2];
        bgColors[0] = color;
        bgColors[1] = darkColor;

        Bitmap bgBitmap = Bitmap.createBitmap(background.getWidth(), background.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas();
        Paint paint = new Paint();
        canvas.setBitmap(bgBitmap);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        LinearGradient gradient = new LinearGradient(0, 0, 0, bgBitmap.getHeight(), bgColors[0], bgColors[1], Shader.TileMode.CLAMP);
        paint.setShader(gradient);
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0, 0, bgBitmap.getWidth(), bgBitmap.getHeight());
        canvas.drawRoundRect(rectF, 20, 20, paint);
        canvas.drawRect(rectF, paint);
        background.setBackground(new BitmapDrawable(bgBitmap));
    }

    private int changeColor(int color) {
        int r = getRed(color);
        int g = getGreen(color);
        int b = getBlue(color);
        // 计算灰度后比较
        if ((r * 299 + g * 587 + b * 114) > 127500) {
            return Color.rgb(r / 6 * 5, g / 6 * 5, b / 6 * 5);
        } else {
            return Color.rgb(r < 188 ? r / 5 * 6 : r, g < 188 ? g / 5 * 6 : g, b < 188 ? b / 5 * 6 : b);
        }
    }

}

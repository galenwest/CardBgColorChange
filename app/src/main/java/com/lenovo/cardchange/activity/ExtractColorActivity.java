package com.lenovo.cardchange.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.lenovo.cardchange.R;

import java.io.FileNotFoundException;

public class ExtractColorActivity extends AppCompatActivity implements View.OnClickListener {

    FrameLayout bg_extract;
    LinearLayout color_card;
    ImageView local_img;
    final int requestCode = 100;
    Bitmap imgBitmap;
    View color_one, color_two, color_three, color_fore, color_five, color_six;
    int one = 0, two = 0, three = 0, fore = 0, five = 0, six = 0;
    TextView time;
    Switch switch_color;
    Button getImg, getColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extract);

        initView();
        initListener();
        initData();
    }

    private void initView() {
        bg_extract = findViewById(R.id.bg_extract);
        color_card = findViewById(R.id.color_card);
        local_img = findViewById(R.id.local_img);
        time = findViewById(R.id.time);
        switch_color = findViewById(R.id.switch_color);
        color_one = findViewById(R.id.color_one);
        color_two = findViewById(R.id.color_two);
        color_three = findViewById(R.id.color_three);
        color_fore = findViewById(R.id.color_fore);
        color_five = findViewById(R.id.color_five);
        color_six = findViewById(R.id.color_six);
        getImg = findViewById(R.id.getImg);
        getColor = findViewById(R.id.getColor);
    }

    private void initListener() {
        getImg.setOnClickListener(this);
        getColor.setOnClickListener(this);
        color_one.setOnClickListener(this);
        color_two.setOnClickListener(this);
        color_three.setOnClickListener(this);
        color_fore.setOnClickListener(this);
        color_five.setOnClickListener(this);
        color_six.setOnClickListener(this);
        if (switch_color.isChecked()) {
            color_card.setVisibility(View.VISIBLE);
        } else {
            color_card.setVisibility(View.INVISIBLE);
        }
        switch_color.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    color_card.setVisibility(View.VISIBLE);
                } else {
                    color_card.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void initData() {
        BitmapDrawable imgDraw = (BitmapDrawable) ContextCompat.getDrawable(ExtractColorActivity.this, R.mipmap.sailing_boat);
        assert imgDraw != null;
        imgBitmap = imgDraw.getBitmap();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.requestCode == requestCode) {
            if (data != null && data.getData() != null) {
                try {
                    imgBitmap = BitmapFactory.decodeStream(
                            getContentResolver().openInputStream(data.getData()));
                    local_img.setImageBitmap(imgBitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getColor:
                obtainColor();
                break;
            case R.id.getImg:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, requestCode);
                break;
            case R.id.color_one:
                switch_color.setChecked(false);
                bg_extract.setBackgroundColor(one);
                break;
            case R.id.color_two:
                switch_color.setChecked(false);
                bg_extract.setBackgroundColor(two);
                break;
            case R.id.color_three:
                switch_color.setChecked(false);
                bg_extract.setBackgroundColor(three);
                break;
            case R.id.color_fore:
                switch_color.setChecked(false);
                bg_extract.setBackgroundColor(fore);
                break;
            case R.id.color_five:
                switch_color.setChecked(false);
                bg_extract.setBackgroundColor(five);
                break;
            case R.id.color_six:
                switch_color.setChecked(false);
                bg_extract.setBackgroundColor(six);
                break;
        }
    }

    private void obtainColor() {
        final long nowTime = System.currentTimeMillis();
        Palette.from(imgBitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                if (palette == null) return;
                one = palette.getVibrantColor(Color.TRANSPARENT);
                two = palette.getDarkVibrantColor(Color.TRANSPARENT);
                three = palette.getLightVibrantColor(Color.TRANSPARENT);
                fore = palette.getMutedColor(Color.TRANSPARENT);
                five = palette.getDarkMutedColor(Color.TRANSPARENT);
                six = palette.getLightMutedColor(Color.TRANSPARENT);

                if (one != Color.TRANSPARENT) {
                    bg_extract.setBackgroundColor(one);
                } else if (fore != Color.TRANSPARENT) {
                    bg_extract.setBackgroundColor(fore);
                } else if (three != Color.TRANSPARENT) {
                    bg_extract.setBackgroundColor(three);
                } else if (six != Color.TRANSPARENT) {
                    bg_extract.setBackgroundColor(six);
                }

                if (one == Color.TRANSPARENT) {
                    one = Color.rgb(255, 255, 255);
                }
                if (two == Color.TRANSPARENT) {
                    two = Color.rgb(255, 255, 255);
                }
                if (three == Color.TRANSPARENT) {
                    three = Color.rgb(255, 255, 255);
                }
                if (fore == Color.TRANSPARENT) {
                    fore = Color.rgb(255, 255, 255);
                }
                if (five == Color.TRANSPARENT) {
                    five = Color.rgb(255, 255, 255);
                }
                if (six == Color.TRANSPARENT) {
                    six = Color.rgb(255, 255, 255);
                }

                color_one.setBackgroundColor(one);
                color_two.setBackgroundColor(two);
                color_three.setBackgroundColor(three);
                color_fore.setBackgroundColor(fore);
                color_five.setBackgroundColor(five);
                color_six.setBackgroundColor(six);
                time.setText("取色耗时：" + (System.currentTimeMillis() - nowTime) + "ms");
            }
        });
    }
}

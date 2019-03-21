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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lenovo.cardchange.R;

import java.io.FileNotFoundException;

public class ExtractColorActivity extends AppCompatActivity {

    LinearLayout bg_extract;
    ImageView local_img;
    final int requestCode = 100;
    Bitmap imgBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extract);
        bg_extract = findViewById(R.id.bg_extract);
        local_img = findViewById(R.id.local_img);
        Button getColor = findViewById(R.id.getColor);
        Button getImg = findViewById(R.id.getImg);
        final TextView time = findViewById(R.id.time);

        getImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, requestCode);
            }
        });

        getColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final long nowTime = System.currentTimeMillis();
//                BitmapDrawable imgDraw = (BitmapDrawable) ContextCompat.getDrawable(ExtractColorActivity.this, R.mipmap.bg_invert_image);
//                assert imgDraw != null;
//                Bitmap imgBitmap = imgDraw.getBitmap();
                Palette.from(imgBitmap).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        if (palette == null) return;
                        if (palette.getDarkVibrantColor(Color.TRANSPARENT) != Color.TRANSPARENT) {
                            bg_extract.setBackgroundColor(palette.getVibrantColor(Color.TRANSPARENT));
                        } else if (palette.getDarkMutedColor(Color.TRANSPARENT) != Color.TRANSPARENT) {
                            bg_extract.setBackgroundColor(palette.getMutedColor(Color.TRANSPARENT));
                        } else {
                            bg_extract.setBackgroundColor(palette.getLightVibrantColor(Color.TRANSPARENT));
                        }
                        time.setText((System.currentTimeMillis() - nowTime) + "ms");
                    }
                });
            }
        });

        BitmapDrawable imgDraw = (BitmapDrawable) ContextCompat.getDrawable(ExtractColorActivity.this, R.mipmap.bg_invert_image);
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
}

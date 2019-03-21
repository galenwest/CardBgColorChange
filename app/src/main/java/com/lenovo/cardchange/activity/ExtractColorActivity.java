package com.lenovo.cardchange.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lenovo.cardchange.R;

public class ExtractColorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extract);
        final View imgColor = findViewById(R.id.imageColor);
        Button getColor = findViewById(R.id.getColor);
        final TextView time = findViewById(R.id.time);

        getColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final long nowTime = System.currentTimeMillis();
                BitmapDrawable imgDraw = (BitmapDrawable) ContextCompat.getDrawable(ExtractColorActivity.this, R.mipmap.bg_invert_image);
                assert imgDraw != null;
                Bitmap imgBitmap = imgDraw.getBitmap();
                Palette.from(imgBitmap).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        if (palette == null) return;
                        if (palette.getDarkVibrantColor(Color.TRANSPARENT) != Color.TRANSPARENT) {
                            imgColor.setBackgroundColor(palette.getVibrantColor(Color.TRANSPARENT));
                        } else if (palette.getDarkMutedColor(Color.TRANSPARENT) != Color.TRANSPARENT) {
                            imgColor.setBackgroundColor(palette.getMutedColor(Color.TRANSPARENT));
                        } else {
                            imgColor.setBackgroundColor(palette.getLightVibrantColor(Color.TRANSPARENT));
                        }
                        time.setText((System.currentTimeMillis() - nowTime) + "ms");
                    }
                });
            }
        });
    }

}

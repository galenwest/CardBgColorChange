package com.lenovo.cardchange.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;

import com.bakerj.infinitecards.AnimationTransformer;
import com.bakerj.infinitecards.CardItem;
import com.bakerj.infinitecards.InfiniteCardView;
import com.bakerj.infinitecards.ZIndexTransformer;
import com.bakerj.infinitecards.transformer.DefaultCommonTransformer;
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
                setStyleLeft(mCardView);
            }
        });
        change_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStyleRight(mCardView);
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

    private void setStyleRight(InfiniteCardView cardView) {
        cardView.setClickable(false);
        cardView.setAnimType(InfiniteCardView.ANIM_TYPE_FRONT_TO_LAST);
        cardView.setAnimInterpolator(new OvershootInterpolator(-8));
        cardView.setTransformerToFront(new DefaultCommonTransformer());
        cardView.setTransformerToBack(new AnimationTransformer() {
            @Override
            public void transformAnimation(View view, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
                int positionCount = fromPosition - toPosition;
                float scale = (0.8f - 0.1f * fromPosition) + (0.1f * fraction * positionCount);
                view.setScaleX(scale);
                view.setScaleY(scale);
                if (fraction < 0.5) {
                    view.setTranslationX(cardWidth * fraction * 1.5f);
                    view.setRotationY(-45 * fraction);
                } else {
                    view.setTranslationX(cardWidth * 1.5f * (1f - fraction));
                    view.setRotationY(-45 * (1 - fraction));
                }
            }

            @Override
            public void transformInterpolatedAnimation(View view, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
                int positionCount = fromPosition - toPosition;
                float scale = (0.8f - 0.1f * fromPosition) + (0.1f * fraction * positionCount);
                view.setTranslationY(-cardHeight * (0.8f - scale) * 0.5f - cardWidth * (0.02f *
                        fromPosition - 0.02f * fraction * positionCount));
            }
        });
        cardView.setZIndexTransformerToBack(new ZIndexTransformer() {
            @Override
            public void transformAnimation(CardItem card, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
                if (fraction < 0.5f) {
                    card.zIndex = 1f + 0.01f * fromPosition;
                } else {
                    card.zIndex = 1f + 0.01f * toPosition;
                }
            }

            @Override
            public void transformInterpolatedAnimation(CardItem card, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {

            }
        });

        cardView.bringCardToFront(1);
    }

    private void setStyleLeft(InfiniteCardView cardView) {
        cardView.setClickable(false);
        cardView.setAnimType(InfiniteCardView.ANIM_TYPE_FRONT_TO_LAST);
        cardView.setAnimInterpolator(new OvershootInterpolator(-8));
        cardView.setTransformerToFront(new DefaultCommonTransformer());
        cardView.setTransformerToBack(new AnimationTransformer() {
            @Override
            public void transformAnimation(View view, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
                int positionCount = fromPosition - toPosition;
                float scale = (0.8f - 0.1f * fromPosition) + (0.1f * fraction * positionCount);
                view.setScaleX(scale);
                view.setScaleY(scale);
                if (fraction < 0.5) {
                    view.setTranslationX(-cardWidth * fraction * 1.5f);
                    view.setRotationY(45 * fraction);
                } else {
                    view.setTranslationX(-cardWidth * 1.5f * (1f - fraction));
                    view.setRotationY(45 * (1 - fraction));
                }
            }

            @Override
            public void transformInterpolatedAnimation(View view, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
                int positionCount = fromPosition - toPosition;
                float scale = (0.8f - 0.1f * fromPosition) + (0.1f * fraction * positionCount);
                view.setTranslationY(-cardHeight * (0.8f - scale) * 0.5f - cardWidth * (0.02f *
                        fromPosition - 0.02f * fraction * positionCount));
            }
        });
        cardView.setZIndexTransformerToBack(new ZIndexTransformer() {
            @Override
            public void transformAnimation(CardItem card, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
                if (fraction < 0.5f) {
                    card.zIndex = 1f + 0.01f * fromPosition;
                } else {
                    card.zIndex = 1f + 0.01f * toPosition;
                }
            }

            @Override
            public void transformInterpolatedAnimation(CardItem card, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {

            }
        });

        cardView.bringCardToFront(1);
    }

}

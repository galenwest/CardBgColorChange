package com.lenovo.cardchange.bean;


public class CardItem {

    private int mTextResource;
    private int mTitleResource;
    private int mImgResource;

    public CardItem(int title, int text, int img) {
        mTitleResource = title;
        mTextResource = text;
        mImgResource = img;
    }

    public int getText() {
        return mTextResource;
    }

    public int getTitle() {
        return mTitleResource;
    }

    public int getImage() {
        return mImgResource;
    }
}

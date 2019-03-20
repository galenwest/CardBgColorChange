package com.lenovo.cardchange;


class CardItem {

    private int mTextResource;
    private int mTitleResource;

    CardItem(int title, int text) {
        mTitleResource = title;
        mTextResource = text;
    }

    int getText() {
        return mTextResource;
    }

    int getTitle() {
        return mTitleResource;
    }
}

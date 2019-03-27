package com.lenovo.cardchange.adapter;

import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.lenovo.cardchange.R;

public class ImageAdapter extends BaseAdapter {
    private int[] resIds;

    public ImageAdapter(int[] resIds) {
        this.resIds = resIds;
    }

    @Override
    public int getCount() {
        return resIds.length;
    }

    @Override
    public Object getItem(int position) {
        return resIds[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .img_adapter, parent, false);
        }
        CardView cardView = convertView.findViewById(R.id.cardView);
        ImageView imageView = convertView.findViewById(R.id.card_image);
        imageView.setImageResource(resIds[position]);
        return convertView;
    }
}

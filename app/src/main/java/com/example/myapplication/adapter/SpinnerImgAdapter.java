package com.example.myapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.myapplication.R;


public class SpinnerImgAdapter extends BaseAdapter {
    private Context context;
    private String[] imgList;

    public SpinnerImgAdapter(Context context, String[] imgList) {
        this.context = context;
        this.imgList = imgList;
    }

    @Override
    public int getCount() {
        return imgList.length;
    }

    @Override
    public Object getItem(int i) {
        return getItem(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = View.inflate(context, R.layout.img_spinner_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mSpinnerItemImg.setImageResource(Integer.valueOf(imgList[i]));
        return view;
    }

    static
    class ViewHolder {
        View view;
        ImageView mSpinnerItemImg;

        ViewHolder(View view) {
            this.view = view;
            this.mSpinnerItemImg = (ImageView) view.findViewById(R.id.spinnerItemImg);
        }

    }
}

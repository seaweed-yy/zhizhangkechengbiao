package com.example.myapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.Bean.CourseListBean;
import com.example.myapplication.R;

import java.util.List;

public class CourseAdapter extends BaseAdapter {
    private Context context;
    private List<CourseListBean> courseListBeanList;

    public CourseAdapter(Context context, List<CourseListBean> courseListBeanList) {
        this.context = context;
        this.courseListBeanList = courseListBeanList;
    }

    @Override
    public int getCount() {
        return courseListBeanList ==null?0: courseListBeanList.size();
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
        if (view==null){
            view = View.inflate(context, R.layout.course_item,null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);

        } else {
            viewHolder= (ViewHolder) view.getTag();
        }
        CourseListBean courseListBean = courseListBeanList.get(i);
        viewHolder.mtimeQuantum.setText(String.valueOf(courseListBean.getTimeQuantum()));
        viewHolder.mmonday.setText(courseListBean.getMonday());
        viewHolder.mtuesday.setText(courseListBean.getTuesday());
        viewHolder.mwednesday.setText(courseListBean.getWednesday());
        viewHolder.mthursday.setText(courseListBean.getThursday());
        viewHolder.mfriday.setText(courseListBean.getFriday());
        return view;
    }
    static
    class ViewHolder{
        View view;
        TextView mtimeQuantum;
        TextView mmonday;
        TextView mtuesday;
        TextView mwednesday;
        TextView mthursday;
        TextView mfriday;

        ViewHolder(View view) {
            this.view=view;
            this.mtimeQuantum=(TextView) view.findViewById(R.id.timeQuantum);
            this.mmonday=(TextView) view.findViewById(R.id.monday);
            this.mtuesday=(TextView) view.findViewById(R.id.tuesday);
            this.mwednesday=(TextView) view.findViewById(R.id.wednesday);
            this.mthursday=(TextView) view.findViewById(R.id.thursday);
            this.mfriday=(TextView) view.findViewById(R.id.friday);
        }
    }
}

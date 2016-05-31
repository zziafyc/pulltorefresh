package com.example.gemptc.myapplicationaa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Gemptc on 2016/4/27.
 */
public class MyAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    Context mContext;
    List<Music> list;

    public MyAdapter(Context context, List<Music> list) {
        mContext = context;
        this.list = list;
        mInflater=LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    class ViewHolder{

        TextView tv_title;
        TextView tv_singer;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.list_item,null);
            viewHolder=new ViewHolder();
            viewHolder.tv_title= (TextView) convertView.findViewById(R.id.title_textview);
            viewHolder.tv_singer= (TextView) convertView.findViewById(R.id.singer_textview);
            convertView.setTag(viewHolder);
        }
        else {
            //说明开始上下滑动，后面的所有行布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Music music = list.get(position);
        viewHolder.tv_title.setText(music.getTitle());
        viewHolder.tv_singer.setText(music.getSinger());

        return convertView;
    }
}

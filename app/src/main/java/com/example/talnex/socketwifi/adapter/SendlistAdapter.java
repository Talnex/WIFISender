package com.example.talnex.socketwifi.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.talnex.socketwifi.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by talnex on 2018/3/15.
 */

public class SendlistAdapter extends BaseAdapter{
    private ArrayList<Integer> ok_list;
    private ArrayList<File> file_list;
    Context context;

    public SendlistAdapter(Context context, ArrayList<File> file_list, ArrayList<Integer> ok_list){
        this.context=context;
        this.file_list=file_list;
        this.ok_list=ok_list;
    }

    @Override
    public int getCount() {
        return file_list.size();
    }

    @Override
    public Object getItem(int position) {
        return file_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=View.inflate(context,R.layout.item_filesend,null);
        TextView textView=convertView.findViewById(R.id.filesend_textview);
        if (ok_list.contains(position)){
            textView.setBackgroundColor(Color.argb(255,196,196,196));
        }
        textView.setText(file_list.get(position).getName());
        return convertView;
    }
}

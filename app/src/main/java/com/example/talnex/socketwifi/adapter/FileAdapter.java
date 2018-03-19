package com.example.talnex.socketwifi.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.talnex.socketwifi.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by talnex on 2018/3/1.
 */

public class FileAdapter extends BaseAdapter {
    Context context;
    List<File> list= new ArrayList<>();
    ArrayList<String> choosed_file_list=new ArrayList<>();

    public FileAdapter(Context context, List<File> list, ArrayList<String> choosed_file_list) {
        this.context=context;
        this.list=list;
        this.choosed_file_list=choosed_file_list;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_filelayout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        File file = (File) list.get(position);
        if (file.isDirectory()) {
            viewHolder.img.setImageResource(R.drawable.folder);
            viewHolder.size.setText("");
            viewHolder.checkBox.setVisibility(View.GONE);
        } else {
            if (choosed_file_list.contains(file.getAbsolutePath())){
                viewHolder.checkBox.setVisibility(View.VISIBLE);
            }else{
                viewHolder.checkBox.setVisibility(View.GONE);
            }
            if (file.getName().endsWith("jpg")||file.getName().endsWith("png")){
                viewHolder.img.setImageResource(R.drawable.photo);
            }else viewHolder.img.setImageResource(R.drawable.unknown);
            if (file.length()>=524288) viewHolder.size.setText(file.length()/1024/1024+" MB");
            else if (file.length()>=1024) viewHolder.size.setText(file.length()/1024+" KB");
            else viewHolder.size.setText(file.length()+" B");
        }
        viewHolder.name.setText(file.getName());
        return convertView;
    }

    class ViewHolder {
        ImageView img;
        TextView name;
        TextView size;
        CheckBox checkBox;

        public ViewHolder(View convertView) {
            img = (ImageView) convertView.findViewById(R.id.imageview_icon);
            name = (TextView) convertView.findViewById(R.id.texiview_name);
            size = (TextView) convertView.findViewById(R.id.texiview_size);
            checkBox = (CheckBox) convertView.findViewById(R.id.checkbox_choose);
        }

    }
}

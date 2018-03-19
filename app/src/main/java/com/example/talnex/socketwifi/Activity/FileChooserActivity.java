package com.example.talnex.socketwifi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.talnex.socketwifi.R;
import com.example.talnex.socketwifi.adapter.FileAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by talnex on 2018/3/6.
 */

public class FileChooserActivity extends AppCompatActivity {
    private ListView listView;
    private android.support.v7.widget.Toolbar toolbar;
    public static final String SDcard= Environment.getExternalStorageDirectory().getAbsolutePath();
    public static String currDir=SDcard;
    FileAdapter adapter;
    List<File> list=new ArrayList<>();
    ArrayList<String> file_choosed_list=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filechooser);
        toolbar= (android.support.v7.widget.Toolbar) findViewById(R.id.file_toolbar);
        setSupportActionBar(toolbar);
        adapter= new FileAdapter(FileChooserActivity.this,list,file_choosed_list);
        listView=(ListView)findViewById(R.id.listview_send);
        choose_file();
    }

    private void choose_file(){
        listView.setVisibility(View.VISIBLE);
        listView.setAdapter(adapter);

        ActivityCompat.requestPermissions(FileChooserActivity.this, new String[]{WRITE_EXTERNAL_STORAGE,MOUNT_UNMOUNT_FILESYSTEMS},2);
        getAllFiles();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File file = list.get(position);
                if (file.isDirectory()) {
                    currDir = file.getAbsolutePath().toString();
                    getAllFiles();
                } else {
                    if (file_choosed_list.contains(file.getAbsolutePath())) {
                        file_choosed_list.remove(file.getAbsolutePath());
                    }
                    else {
                        file_choosed_list.add(file.getAbsolutePath());
                    }
                    adapter.notifyDataSetChanged();//刷新一下checkbox视图
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (currDir.equals(SDcard)) {
            super.onBackPressed();
        } else {
            currDir = new File(currDir).getParent();
            getAllFiles();
        }
    }
    public void getAllFiles(){
        list.clear();
        final File file=new File(currDir);
        if (file.isDirectory()){
            File[] files=file.listFiles();
            if (files!=null){
                for (File file2:files){
                    list.add(file2);
                }
            }
        }
        else{
            list.add(file);
        }
        sort();
        adapter.notifyDataSetChanged();
    }

    private void sort(){
        Collections.sort(list, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.isDirectory()&&o2.isDirectory()||o1.isFile()&&o2.isFile()){
                    return o1.compareTo(o2);
                }
                return o1.isDirectory()?-1:1;
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.file_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.toolbar_ok){
            if (file_choosed_list.isEmpty()) {
                Toast.makeText(FileChooserActivity.this,"文件队列为空",Toast.LENGTH_SHORT).show();
            }else {
                Intent intent=new Intent(FileChooserActivity.this,FileManagerActivity.class);
                intent.putStringArrayListExtra("filelist",file_choosed_list);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

}

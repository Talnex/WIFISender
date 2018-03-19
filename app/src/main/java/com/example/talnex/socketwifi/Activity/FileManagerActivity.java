package com.example.talnex.socketwifi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.beiing.flikerprogressbar.FlikerProgressBar;
import com.example.talnex.socketwifi.R;
import com.example.talnex.socketwifi.adapter.SendlistAdapter;
import com.example.talnex.socketwifi.wifitools.ApMgr;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.CHANGE_NETWORK_STATE;
import static android.Manifest.permission.CHANGE_WIFI_STATE;

/**
 * Created by talnex on 2018/3/4.
 */

public class FileManagerActivity extends AppCompatActivity {
    ArrayList<String> choosed_file_list = new ArrayList<>();
    ArrayList<File> file_list = new ArrayList<>();
    ArrayList<Integer> ok_list = new ArrayList<>();
    ArrayList<String> connet_list = new ArrayList<>();
    ArrayList<String> connet_ip_list = new ArrayList<>();
    String name = MainActivity.name;
    int position = 0;
    ArrayAdapter<String> connetlistadapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filemanager_layout);
        Intent intent = getIntent();
        choosed_file_list = intent.getStringArrayListExtra("filelist");

        for (int i = 0; i < choosed_file_list.size(); i++) {
            file_list.add(new File(choosed_file_list.get(i)));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.send_toolbar);
        setSupportActionBar(toolbar);

        FlikerProgressBar progressBar = findViewById(R.id.flickerprogressbar);
        ListView listView_sendfilelist = findViewById(R.id.file_send_prosessbar_listview);
        ListView listView_connetlist = findViewById(R.id.connet_listview);

        progressBar.setProgressText(choosed_file_list.get(position));
        progressBar.setProgress(50);

        SendlistAdapter sendfilelistadapter = new SendlistAdapter(FileManagerActivity.this, file_list, ok_list);
        listView_sendfilelist.setAdapter(sendfilelistadapter);

        ActivityCompat.requestPermissions(FileManagerActivity.this, new String[]{
                CHANGE_NETWORK_STATE
                , CHANGE_WIFI_STATE,
                ACCESS_NETWORK_STATE,
                ACCESS_WIFI_STATE}, 6);
        create_ap(name);

        connetlistadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, connet_list);
        listView_connetlist.setAdapter(connetlistadapter);


    }

    private void create_ap(String name) {
        ApMgr.closeWifi(FileManagerActivity.this);
        if (ApMgr.isApOn(FileManagerActivity.this)) {
            ApMgr.closeAp(FileManagerActivity.this);
        }
        ApMgr.openAp(FileManagerActivity.this, "@#" + name, name);
        Toast.makeText(FileManagerActivity.this, "wifi" + name + "已创建", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ApMgr.closeAp(FileManagerActivity.this);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.send_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_ok: {
                if (connet_list.isEmpty()) {
                    Toast.makeText(FileManagerActivity.this, "等待队列为空", Toast.LENGTH_SHORT).show();
                } else {

                }
                break;
            }
            case R.id.toolbar_refresh: {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("test","创建");
                        DatagramSocket datagramSocket = null;
                        try {
                            datagramSocket = new DatagramSocket(10000);
                            byte[] buf = new byte[1024];
                            DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
                            Log.d("test","等待接受");
                            datagramSocket.receive(datagramPacket);
                            Log.d("test","接受完了");
                            String data = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                            if (!connet_list.contains(data)) {
                                connet_list.add(data);
                                connet_ip_list.add(datagramPacket.getAddress().getHostAddress());
                                Log.d("test","接收到"+data+datagramPacket.getAddress().getCanonicalHostName());
                            }
                        } catch (SocketException e) {
                            Log.d("test","创建失败");
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (datagramSocket != null) {
                            datagramSocket.close();
                        }
                    }
                }).start();
                connetlistadapter.notifyDataSetChanged();
                break;
            }


        }
        return super.onOptionsItemSelected(item);
    }


}
package com.example.talnex.socketwifi.Activity;

import android.content.Intent;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.talnex.socketwifi.R;
import com.example.talnex.socketwifi.wifitools.WifiMgr;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.CHANGE_NETWORK_STATE;
import static android.Manifest.permission.CHANGE_WIFI_STATE;

/**
 * Created by talnex on 2018/2/28.
 */

public class ReciveActivity extends AppCompatActivity{
    List<ScanResult> apList=new ArrayList<>();
    ArrayList<String> suit_result=new ArrayList<>();
    private android.support.v7.widget.Toolbar toolbar;

    ArrayAdapter<String> adapter;
    WifiMgr mgr;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mode_recive);
        toolbar = (Toolbar) findViewById(R.id.wifis_toolbar);
        setSupportActionBar(toolbar);

        ActivityCompat.requestPermissions(ReciveActivity.this,new String[]{
                CHANGE_NETWORK_STATE,
                CHANGE_WIFI_STATE,
                ACCESS_NETWORK_STATE,
                ACCESS_WIFI_STATE},7);
        mgr=new WifiMgr(ReciveActivity.this);
        mgr.openWifi();

    }

    private ArrayList<String> suit_wifi(List<ScanResult> scanResults){
        ArrayList<String> list=new ArrayList<>();
        for (int i = 0;i<scanResults.size();i++){
            if (scanResults.get(i).SSID.substring(0,2).equals("@#")){
                list.add(scanResults.get(i).SSID.substring(2,10));
            }
        }
        if (list.isEmpty()) {
            list.add("请刷新重试");
        }
        return list;
    }

    private void getresult(){
        apList.clear();
        suit_result.clear();
        mgr.startScan();
        try {
            apList=mgr.getWifiScanList();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        suit_result=suit_wifi(apList);
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,suit_result);
        ListView listView=findViewById(R.id.wifis_listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ReciveActivity.this,"已选择"+suit_result.get(position),Toast.LENGTH_SHORT).show();
                String key = suit_result.get(position);
                try {
                    mgr.connectWifi("@#"+key,key,apList);
                } catch (InterruptedException e) {}
                Intent intent=new Intent(ReciveActivity.this,FileReciveActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.wifi_toolbar,menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mgr.closeWifi();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.toolbar_refresh){
            getresult();
        }
        return super.onOptionsItemSelected(item);
    }
}

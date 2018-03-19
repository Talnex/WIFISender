package com.example.talnex.socketwifi.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.talnex.socketwifi.R;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;


/**
 * Created by talnex on 2018/3/16.
 */

public class FileReciveActivity extends AppCompatActivity {
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filerecive_layout);
        toolbar = findViewById(R.id.recive_toolbar);
        setSupportActionBar(toolbar);


//        new Thread(new Runnable() {//接收文件进程
//            @Override
//            public void run() {
//                try {
//                    ServerSocket serverSocket=new ServerSocket(10001);
//                    while (true){
//                        Socket s=serverSocket.accept();
//                        byte[] buf=new byte[1024];
//
//
//
//                    }
//
//
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.wifi_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.toolbar_refresh) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    DatagramSocket datagramSocket = null;
                    try {
                        datagramSocket = new DatagramSocket(20000);
                        byte[] buf = MainActivity.name.getBytes();
                        DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length,
                                InetAddress.getByName("192.168.43.255"), 10000);
                        for (int i=0;i<5;i++){
                            datagramSocket.send(datagramPacket);
                            Log.d("test","已发送"+MainActivity.name);
                            Thread.sleep(200);
                        }
                        datagramSocket.close();
                    } catch (SocketException e) {
                        e.printStackTrace();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        }
        return super.onOptionsItemSelected(item);
    }
}
package com.example.talnex.socketwifi.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;

import android.widget.EditText;

import android.widget.Toast;

import com.example.talnex.socketwifi.R;


/**
 * Created by talnex on 2018/2/28.
 */

public class MainActivity extends AppCompatActivity{
    public static String name= "";
    private EditText editText;
    private Button button;
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=(EditText)findViewById(R.id.edittext_name);
        button =(Button)findViewById(R.id.button_send_nameok);
        toolbar= (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_send);
        AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("提示");
        dialog.setMessage("请输入一个8位英文字母昵称");
        dialog.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=editText.getText().toString();
                if (check_name(name)){
                    Toast.makeText(MainActivity.this,"成功设置昵称",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this,ChooseModeActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this,"输入的昵称不对哦",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean check_name(String name){
        if (name.length()==8)
            return true;
        else return false;
    }

}

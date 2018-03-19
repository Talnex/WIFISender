package com.example.talnex.socketwifi.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;


import com.example.talnex.socketwifi.R;

public class ChooseModeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mode_choose);
        Button send_button=(Button)findViewById(R.id.button_mode_send);
        Button recive_button=(Button)findViewById(R.id.button_mode_recive);
        toolbar= (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChooseModeActivity.this,FileChooserActivity.class);
                startActivity(intent);
            }
        });


        recive_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChooseModeActivity.this,ReciveActivity.class);
                startActivity(intent);
            }
        });
    }

}



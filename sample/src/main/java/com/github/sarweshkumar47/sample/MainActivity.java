package com.github.sarweshkumar47.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button libDemo = (Button) findViewById(R.id.demo);
        Button mapDemo = (Button) findViewById(R.id.mapDemo);
        Button toolbarDemo = (Button) findViewById(R.id.toolBarDemo);


        libDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LibDemoActivity.class);
                startActivity(i);
            }
        });

        mapDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MapDemoActivity.class);
                startActivity(i);
            }
        });

        toolbarDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ToolbarDemoActivity.class);
                startActivity(i);
            }
        });
    }
}

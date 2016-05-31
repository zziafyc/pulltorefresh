package com.example.gemptc.myapplicationaa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import ultraPullToRefresh.UltraPullToRefreshActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void pulltorefresh(View view) {
        Intent intent=new Intent(MenuActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void ultrapulltorefresh(View view) {
        Intent intent=new Intent(MenuActivity.this, UltraPullToRefreshActivity.class);
        startActivity(intent);
    }
}

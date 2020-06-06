package com.example.illuminate00;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainPage extends AppCompatActivity {


    private Button ViewRlist;
    private Button logout;
    private Button settings;
    private Button viewprofile;
    private Button Viewmyapps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        ViewRlist = findViewById(R.id.ViewRlist);
        logout = findViewById(R.id.logout);
        settings = findViewById(R.id.settings);
        viewprofile = findViewById(R.id.viewprofile);
        Viewmyapps = findViewById(R.id.Viewmyapps);


        ViewRlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPage.this, Homepage.class));
            }
        });

        viewprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPage.this, Profile.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPage.this, MainActivity.class));
            }
        });

        Viewmyapps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPage.this, installedapps.class));
                //startActivity(new Intent(MainPage.this, MyAppsList.class));
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPage.this, SettingsActivity.class));
            }
        });

    }
}

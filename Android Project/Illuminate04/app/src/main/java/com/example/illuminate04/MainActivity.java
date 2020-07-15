package com.example.illuminate04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button loginbutton;
    private TextView register;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String UName, pass,updatedata;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        UName = sh.getString("username", "");
        pass = sh.getString("password", "");

        if (UName != null && !UName.equals("") && pass != null && !pass.equals("")) {
            startActivity(new Intent(MainActivity.this, Home.class));
        }


        //startActivity(new Intent(MainActivity.this, Home.class));
        // getSupportActionBar().hide();
        loginbutton = findViewById(R.id.loginbutton);
        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUpPage.class));
                finish();
            }
        });

        loginbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Loginpage.class));
                finish();
            }
        });
        //installedapps();
    }



    public void installedapps() {
        Log.i("afdlk", "asdf");
        List<PackageInfo> packagelist = getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packagelist.size(); i++) {
            Log.i("afdlk", "asdf");
            PackageInfo packageInfo = packagelist.get(i);
            if ((packageInfo.applicationInfo.flags & (packageInfo.applicationInfo.FLAG_UPDATED_SYSTEM_APP | ApplicationInfo.FLAG_SYSTEM)) > 0) {
                String appname = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                Log.i("System App name: " + Integer.toString(i), appname);
            }else{
                String appname = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                Log.i("Installed app name: " + Integer.toString(i), appname);
            }
        }



    }

}

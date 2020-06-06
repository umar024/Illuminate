     package com.example.illuminate00;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

     public class MainActivity extends AppCompatActivity {

    private Button loginbutton;
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //startActivity(new Intent(MainActivity.this, Profile.class));
       // getSupportActionBar().hide();
       loginbutton = findViewById(R.id.loginbutton);
       register = findViewById(R.id.register);

       register.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
               startActivity(new Intent(MainActivity.this, SignUpPage.class));
           }
       });

       loginbutton.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               startActivity(new Intent(MainActivity.this, Loginpage.class));
           }
       });
        installedapps();
    }

    public void installedapps(){
        Log.i("afdlk","asdf");
        List<PackageInfo> packagelist = getPackageManager().getInstalledPackages(0);
        for(int i=0; i<packagelist.size(); i++){
            Log.i("afdlk","asdf");
            PackageInfo packageInfo = packagelist.get(i);
            if((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM)==0){
                String appname = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                Log.i("App name: "+ Integer.toString(i),appname);
            }
        }
    }

}

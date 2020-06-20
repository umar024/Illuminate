package com.example.illuminate04;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Loginpage extends AppCompatActivity {


    //private APIService mAPIService;
    String usernameindb;
    private ImageView backbutton;
    private TextView register;
    private EditText usernameview;
    private EditText passwordview ;
    private Button btnLogin;
    private static final String Register_URL = "https://nasfistsolutions.com/illuminate/connection.php";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        // getSupportActionBar().hide();

        btnLogin = findViewById(R.id.btnLogin);
        backbutton = findViewById(R.id.backbtn);
        register = findViewById(R.id.register);
        usernameview = findViewById(R.id.username);
        passwordview = findViewById(R.id.password);
        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        myEdit = sharedPreferences.edit();


        // mAPIService = ApiUtils.getAPIService();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Loginpage.this, SignUpPage.class));
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginuser();
            }
        });

    }


    public String getUsernameindb(){
        return usernameindb;
    }
    public void loginuser() {
        final String username = usernameview.getText().toString().trim().toLowerCase();
        final String password = passwordview.getText().toString().trim().toLowerCase();
        usernameindb = username;

        String urlsuffix = "?username="+username+"&password="+password+"&action=login";

        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loading=ProgressDialog.show(Loginpage.this, "Please Wait",null,true,true);
            }
            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(),s, Toast.LENGTH_SHORT).show();
                loading.cancel();
                if(s.equals("login successful")){


                    myEdit.putString("username", username);
                    myEdit.putString("password", password);
                    myEdit.commit();
                    startActivity(new Intent(Loginpage.this, Home.class));
                }
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                try{
                    URL url = new URL(Register_URL+s);
                    HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
                    bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String result = bufferedReader.readLine();
                    return result;

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "No internet Connection",Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
        }
        RegisterUser ur = new RegisterUser();
        ur.execute(urlsuffix);
    }
}

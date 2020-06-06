package com.example.illuminate00;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class SignUpPage extends AppCompatActivity {

    private TextView loginbtn;
    private ImageView backbutton;
    private EditText edit_username;
    private EditText edit_password;
    private EditText edit_email;
    private Button btnSignup;
    private static final String Register_URL = "https://nasfistsolutions.com/illuminate/connection.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        //getSupportActionBar().hide();
        backbutton = findViewById(R.id.backbtn);
        loginbtn = findViewById(R.id.btnLogin);
        edit_username = findViewById(R.id.username);
        edit_password = findViewById(R.id.password);
        edit_email = findViewById(R.id.email);
        btnSignup = findViewById(R.id.btnsignup);

        btnSignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                RegisterUser();
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpPage.this, Loginpage.class));
            }
        });


    }

    public void RegisterUser() {
        String username = edit_username.getText().toString().trim().toLowerCase();
        String password = edit_password.getText().toString().trim().toLowerCase();
        String email = edit_email.getText().toString().trim().toLowerCase();
        register(username, password, email);
    }

        public void register(String username, String password, String email){
        String urlsuffix = "?username="+username+"&password="+password+"&email="+email+"&action=signup";


        class RegisterUser extends AsyncTask<String, Void, String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loading=ProgressDialog.show(SignUpPage.this, "Please Wait",null,true,true);
            }
            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(),s, Toast.LENGTH_SHORT).show();
                loading.cancel();
                if(s.equals("Sign Up Successful!")){
                    startActivity(new Intent(SignUpPage.this, MainPage.class));
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




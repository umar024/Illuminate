package com.example.illuminate04;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        mAuth = FirebaseAuth.getInstance();
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
                startActivity(new Intent(Loginpage.this, MainActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginuserfirebase();
            }
        });

    }

    private void killActivity(){
        finish();
    }


    public String getUsernameindb(){
        return usernameindb;
    }

    public void loginuserfirebase(){
        final String email = usernameview.getText().toString().trim().toLowerCase();
        final String password = passwordview.getText().toString().trim().toLowerCase();

        if(email.isEmpty()){
            usernameview.setError("Email is required!");
            usernameview.requestFocus();
            return;
        }
        if(password.isEmpty()){
            passwordview.setError("Password is required!");
            passwordview.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            usernameview.setError("Enter a valid email");
            usernameview.requestFocus();
            return;
        }
        if(password.length()<6){
            passwordview.setError("Password is too short!");
            passwordview.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
                    myEdit.putString("username", email);
                    myEdit.putString("password", password);
                    myEdit.commit();
                    startActivity(new Intent(Loginpage.this, Home.class));
                    finish();
                    //startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Authentication failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /*public void loginuser() {
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
                    killActivity();
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
    }*/
}

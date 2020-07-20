package com.example.illuminate04;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.OnFailureListener;
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
    private TextView forgotpassword;
    private EditText usernameview;
    private EditText passwordview ;
    private Button btnLogin;
    private static final String Register_URL = "https://nasfistsolutions.com/illuminate/authentication.php";
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
        forgotpassword = findViewById(R.id.forgetpassword);
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

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isFinishing()){
                    showdialogue();
                }
                final EditText resetmail = new EditText(Loginpage.this);
                final AlertDialog.Builder passworddialogue = new AlertDialog.Builder(Loginpage.this);
                passworddialogue.setTitle("Forgot password?");
                passworddialogue.setMessage("Enter your Email:");
                passworddialogue.setView(resetmail);

                passworddialogue.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail = resetmail.getText().toString();
                        if(mail.isEmpty()){
                            resetmail.setError("Email is required!");
                            resetmail.requestFocus();
                            return;
                        }
                        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                            resetmail.setError("Enter a valid email");
                            resetmail.requestFocus();
                            return;
                        }
                        mAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getApplicationContext(), "Reset Link Sent!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Could not send Reset Link", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                });
                passworddialogue.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                passworddialogue.show();

            }
        });
    }

    private void showdialogue(){


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
                    myEdit.putString("updatedata","");
                    myEdit.putString("mycategory","");
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

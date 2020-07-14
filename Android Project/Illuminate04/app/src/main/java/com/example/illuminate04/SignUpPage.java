package com.example.illuminate04;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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
    private EditText edit_confirmpassword;
    private Button btnSignup;
    private FirebaseAuth mAuth;
    private static final String Register_URL = "https://nasfistsolutions.com/illuminate/authentication.php";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        mAuth = FirebaseAuth.getInstance();

        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        myEdit = sharedPreferences.edit();

        //getSupportActionBar().hide();
        backbutton = findViewById(R.id.backbtn);
        loginbtn = findViewById(R.id.btnLogin);
        edit_username = findViewById(R.id.username);
        edit_password = findViewById(R.id.password);
        edit_email = findViewById(R.id.email);
        edit_confirmpassword = findViewById(R.id.confirmpassword);
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
                startActivity(new Intent(SignUpPage.this, MainActivity.class));
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
        final String username = edit_username.getText().toString().trim().toLowerCase();
        final String password = edit_password.getText().toString().trim().toLowerCase();
        String email = edit_email.getText().toString().trim().toLowerCase();
        String confirmpassword = edit_confirmpassword.getText().toString().trim().toLowerCase();

        if(email.isEmpty()){
            edit_email.setError("Email is required");
            edit_email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edit_email.setError("Enter valid email");
            edit_email.requestFocus();
            return;
        }
        if(password.isEmpty()){
            edit_password.setError("Password is required");
            edit_password.requestFocus();
            return;
        }
        if(password.length() < 6){
            edit_password.setError("Password is too short");
            edit_password.requestFocus();
            return;
        }
        if(confirmpassword.isEmpty()){
            edit_confirmpassword.setError("Confirm Password is required");
            edit_confirmpassword.requestFocus();
            return;
        }
        if(!confirmpassword.equals(password)){
            Toast.makeText(getApplicationContext(),"Confirm password does not match", Toast.LENGTH_SHORT).show();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Toast.makeText(getApplicationContext(),"User Registered Successfuly", Toast.LENGTH_SHORT).show();
                //putindb();
                myEdit.putString("username", username);
                myEdit.putString("password", password);
                myEdit.commit();
                startActivity(new Intent(SignUpPage.this, Home.class));
                finish();
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }
        });

        //        register(username, password, email);
    }

    public void register(final String username, final String password, String email){
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
                    myEdit.putString("username", username);
                    myEdit.putString("password", password);
                    myEdit.commit();
                    startActivity(new Intent(SignUpPage.this, Home.class));
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




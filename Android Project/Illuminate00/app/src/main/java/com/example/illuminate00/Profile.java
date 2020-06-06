package com.example.illuminate00;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Profile extends AppCompatActivity {

    public static final String url = "https://nasfistsolutions.com/illuminate/listapps.php";
    TextView username, email, contact, enteremail, entercontact;
    String requestdata;
    Loginpage obj = new Loginpage();
    String reqid = obj.getUsernameindb();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        contact = findViewById(R.id.contact);
        enteremail = findViewById(R.id.enteremail);
        entercontact = findViewById(R.id.entercontact);


        requestdata = "?request=3&id=4";

        loaddata();
    }

    public void loaddata() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("fetching data");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url + "" + requestdata,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("details");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                email.setText(o.getString("email"));
                                contact.setText(o.getString("contact"));
                                username.setText(o.getString("username"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
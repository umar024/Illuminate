package com.example.illuminate04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class AppDetails extends AppCompatActivity {


    public static final String url = "https://nasfistsolutions.com/illuminate/getapp.php";
    ImageView appicon;
    ImageView headericon;
    TextView apptitle;
    TextView description;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_details);
        Intent intent = getIntent();
        String id = intent.getStringExtra("appid");
        appicon = findViewById(R.id.appicon);
        apptitle = findViewById(R.id.Title);
        headericon = findViewById(R.id.headerimage);
        description = findViewById(R.id.Descriptiondetails);


        //Toast.makeText(this, id, Toast.LENGTH_LONG).show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url + "?appid=" + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("details");


                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                items item;
                                Log.i("a;fsdk", o.getString("title"));

                                item = new items(
                                        o.getString("icon"),
                                        o.getInt("id"),
                                        o.getString("title"),
                                        o.getString("description"),
                                        o.getInt("reviews"),
                                        o.getString("score")
                                );
                                Picasso.get().load(item.getImage()).into(appicon);
                                Picasso.get().load(o.getString("headerImage")).into(headericon);
                                description.setText(o.getString("description"));
                                apptitle.setText(o.getString("title"));

                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
}

package com.example.illuminate04;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AppDetails extends AppCompatActivity {

    public static final String url = "https://nasfistsolutions.com/illuminate/getapp.php";
    public static final String url1 = "https://nasfistsolutions.com/illuminate/bookmark.php";
    ImageView appicon;
    TextView apptitle;
    ImageView backbutton;
    ImageView bmbutton;
    RecyclerView recyclerView;
    List<image> imagesList;
    imageAdapter adapter;
    String mypackage = "";
    Button installbutton;
    TextView description;
    TextView installs;
    TextView ratings;
    TextView size;
    TextView version;
    boolean isbookmark;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_details);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading data");
        progressDialog.show();
        mAuth = FirebaseAuth.getInstance();

        isbookmark = false;
        Intent intent = getIntent();
        final String id = intent.getStringExtra("appid");
        //Toast.makeText(this, id, Toast.LENGTH_LONG).show();
        appicon = findViewById(R.id.appicon);
        apptitle = findViewById(R.id.Title);
        backbutton = findViewById(R.id.btnback);
        bmbutton = findViewById(R.id.bmbtn);
        recyclerView = findViewById(R.id.recyclerview);
        imagesList = new ArrayList<>();
        installbutton = findViewById(R.id.installbutton);
        description = findViewById(R.id.description);
        installs = findViewById(R.id.installs);
        ratings = findViewById(R.id.ratings);
        size = findViewById(R.id.size);
        version = findViewById(R.id.version);
        final String userid = mAuth.getCurrentUser().getUid();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                AppDetails.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        installbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + mypackage));
                startActivity(intent);
            }
        });

        bmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                int request = 0;
                //isbookmark = false; //////////////////////////////////////////////HAVE TO CHANGE LATER
                if (!isbookmark) { //if app is not already in bookmarks
                    request = 1;
                    v.setBackgroundResource(R.drawable.ic_bookmark_selected);
                } else {
                    v.setBackgroundResource(R.drawable.ic_bookmark_unselected);
                    request = 2;
                }


                StringRequest stringRequest = new StringRequest(Request.Method.GET,
                        url1 + "?request=" + request + "&appid=" + id + "&userid=" + userid,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    if (response.equals("added")) {
                                        Toast.makeText(v.getContext(), "Added to Bookmarks", Toast.LENGTH_SHORT).show();
                                        isbookmark = true;
                                    }else if(response.equals("deleted")){
                                        Toast.makeText(v.getContext(), "Removed Bookmark", Toast.LENGTH_SHORT).show();
                                        isbookmark = false;
                                    }
                                } catch (Exception e) {
                                    Log.e("error: ", "" + e);
                                    if(isbookmark)
                                    Toast.makeText(v.getContext(), "error! please try later", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if(isbookmark)
                                    v.setBackgroundResource(R.drawable.ic_bookmark_selected);
                                else{
                                    v.setBackgroundResource(R.drawable.ic_bookmark_unselected);
                                }
                                Toast.makeText(getApplicationContext(), "Error! Check your internet", Toast.LENGTH_SHORT).show();

                            }
                        });
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);

                //v.setBackgroundResource(R.drawable.ic_bookmark_selected);
            }
        });


        //Toast.makeText(this, id, Toast.LENGTH_LONG).show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url + "?appid=" + id + "&userid=" + userid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray arrayobjectdetails = jsonObject.getJSONArray("details");
                            JSONArray arrayscreenshots = jsonObject.getJSONArray("detailsSS");



                            for (int i = 0; i < arrayobjectdetails.length(); i++) {
                                JSONObject o = arrayobjectdetails.getJSONObject(i);
                                Picasso.get().load(o.getString("icon")).into(appicon);
                                description.setText(o.getString("description"));
                                apptitle.setText(o.getString("title"));
                                mypackage = o.getString("package");
                                installs.setText(o.getString("installs"));
                                ratings.setText(o.getString("score").substring(0, 3));
                                size.setText(o.getString("size")+"B");
                                version.setText(o.getString("version"));
                                isbookmark = o.getBoolean("isbookmark");
                                if(isbookmark){
                                    bmbutton.setBackgroundResource(R.drawable.ic_bookmark_selected);
                                    isbookmark = true;
                                }
                            }
                            for (int i = 0; i < arrayscreenshots.length(); i++) {
                                JSONObject object = arrayscreenshots.getJSONObject(i);
                                image image;
                                image = new image(object.getInt("idssapp"), object.getString("screenshot"));
                                imagesList.add(image);
                            }

                            adapter = new imageAdapter(getApplicationContext(), imagesList);
                            recyclerView.setAdapter(adapter);

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

package com.example.illuminate00;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.List;

public class Homepage extends AppCompatActivity {



    public static final String url = "https://nasfistsolutions.com/illuminate/listapps.php";
    RecyclerView recyclerView;
    itemAdapter adapter;
    List<items> itemsList;
    int getreq =0;
    ActionBar actionBar;
    Toolbar toolbar;
    Button updateButton;
    ImageView backbtn;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "action"+item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()){
            case R.id.action_settings:
                finish();
            break;
            case R.id.updatebutton:
                getreq = 1;
                loadRecyclerViewData();
            break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        updateButton = findViewById(R.id.Updatebutton);
        backbtn = findViewById(R.id.btnback);

        backbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });



        //getSupportActionBar().hide();

       /** toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
*/



        itemsList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //add data to itemsList
        //itemsList.add(new items(R.drawable.appicon, 0, "whatsapp", "4.5",1777, "10.5MB"));
        getreq=1;
        loadRecyclerViewData();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getreq = 2;
                loadRecyclerViewData();
            }
        });


    }
    public void loadRecyclerViewData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("updating suggestion list");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url+"?request="+getreq,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("details");

                            for(int i=0; i<array.length();i++){
                                JSONObject o = array.getJSONObject(i);
                                items item;
                                if(i%3 == 0 ){
                                    item = new items(
                                        R.drawable.appicon,
                                        o.getInt("id"),
                                        o.getString("app"),
                                        o.getString("rating"),
                                        o.getInt("reviews"),
                                        o.getString("size")
                                    );
                                }else if(i%2==0){
                                    item = new items(
                                            R.drawable.appicon1,
                                            o.getInt("id"),
                                            o.getString("app"),
                                            o.getString("rating"),
                                            o.getInt("reviews"),
                                            o.getString("size")
                                    );
                                }else{
                                    item = new items(
                                            R.drawable.appicon2,
                                            o.getInt("id"),
                                            o.getString("app"),
                                            o.getString("rating"),
                                            o.getInt("reviews"),
                                            o.getString("size")
                                    );
                                }
                                itemsList.add(item);
                            }
                            adapter = new itemAdapter(getApplicationContext(),itemsList);
                            recyclerView.setAdapter(adapter);

                        }

                        catch (JSONException e) {
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

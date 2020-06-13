package com.example.illuminate04;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements itemAdapter.OnAppListener {


    public HomeFragment() {
        // Required empty public constructor
    }

    public static final String url = "https://nasfistsolutions.com/illuminate/listapps.php";
    RecyclerView recyclerView;
    itemAdapter adapter;
    List<items> itemsList;
    int getreq;
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        itemsList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        getreq = 1;
        loadRecyclerViewData();
    }

    public void loadRecyclerViewData() {
        final ProgressDialog progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("updating suggestion list");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url + "?request=" + getreq,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("details");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                items item;
                                //Log.i("a;fsdk", o.getString("title"));

                                item = new items(
                                        o.getString("icon"),
                                        o.getInt("id"),
                                        o.getString("title"),
                                        o.getString("rating"),
                                        o.getInt("reviews"),
                                        o.getString("score")
                                );
                                itemsList.add(item);
                            }
                            adapter = new itemAdapter(view.getContext(), itemsList, HomeFragment.this);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(view.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        requestQueue.add(stringRequest);


    }


    @Override
    public void onAppClick(int position) {
        itemsList.get(position);
        Intent intent = new Intent(view.getContext(), AppDetails.class);
        intent.putExtra("appid", ""+itemsList.get(position).getId());
        startActivity(intent);
    }
}
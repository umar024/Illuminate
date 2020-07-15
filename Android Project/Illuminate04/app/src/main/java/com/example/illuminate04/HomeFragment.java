package com.example.illuminate04;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements itemAdapter.OnAppListener {


    public HomeFragment() {
        // Required empty public constructor
    }

    public static final String url = "https://nasfistsolutions.com/illuminate/listapps.php";
    private static final String url1 = "https://nasfistsolutions.com/illuminate/authentication.php";
    public static final String urlgetpermissions = "https://nasfistsolutions.com/illuminate/permissions.php";
    RecyclerView recyclerView;
    itemAdapter adapter;
    List<items> itemsList;
    String getreq;
    String genreid;
    public View view;
    TextView sortbutton, categoriesbutton;
    SearchView searchView;
    List<PackageInfo> listn;
    String[] names;
    private FirebaseAuth mAuth;
    String userid;
    String mycategory = "";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        itemsList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerview);
        searchView = view.findViewById(R.id.searchview);
        searchView.clearFocus();
        sortbutton = view.findViewById(R.id.sortbutton);
        categoriesbutton = view.findViewById(R.id.categoriesbutton);
        listn = getContext().getPackageManager().getInstalledPackages(0);
        names = new String[listn.size()];
        mAuth = FirebaseAuth.getInstance();
        userid = mAuth.getCurrentUser().getUid();

        sharedPreferences = this.getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String updatedata = "";
        String categorysharedpreference="";
        myEdit = sharedPreferences.edit();

        updatedata = sharedPreferences.getString("updatedata", "");
        categorysharedpreference = sharedPreferences.getString("mycategory","");

        if (updatedata.equals("")) {
            myEdit.putString("updatedata", "done");
            myEdit.commit();
            savedata();
        }
        if (categorysharedpreference.equals("")) {
            getmycategory();
        }else{
            mycategory = sharedPreferences.getString("mycategory","");
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        getreq = "1";
        genreid="";
        loadRecyclerViewData();


        sortbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(view.getContext(), v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.sortmenu, popup.getMenu());
                popup.show();

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().toString().equals("App Name")) {
                            Collections.sort(itemsList, new Comparator<items>() {
                                public int compare(items v1, items v2) {
                                    return v1.getAppname().compareTo(v2.getAppname());
                                }
                            });
                        } else if (item.getTitle().toString().equals("Ratings")) {
                            Collections.sort(itemsList, new Comparator<items>() {
                                @Override
                                public int compare(items v1, items v2) {
                                    return v2.getScore().compareTo(v1.getScore());
                                }
                            });
                        } else if (item.getTitle().toString().equals("Downloads")) {
                            Collections.sort(itemsList, new Comparator<items>() {
                                @Override
                                public int compare(items v1, items v2) {
                                    return v2.getInstalls().compareTo(v1.getInstalls());
                                }
                            });
                        } else if (item.getTitle().toString().equals("Size: High-Low")) {
                            Collections.sort(itemsList, new Comparator<items>() {
                                @Override
                                public int compare(items v1, items v2) {
                                    return v2.getSize().compareTo(v1.getSize());
                                }
                            });
                        } else if (item.getTitle().toString().equals("Size: Low-High")) {
                            Collections.sort(itemsList, new Comparator<items>() {
                                @Override
                                public int compare(items v1, items v2) {
                                    return v1.getSize().compareTo(v2.getSize());
                                }
                            });
                        }
                        adapter = new itemAdapter(view.getContext(), itemsList, HomeFragment.this);
                        recyclerView.setAdapter(adapter);
                        return true;
                    }
                });
            }
        });

        categoriesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(view.getContext(), v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.categoriesmenu, popup.getMenu());
                popup.show();

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().toString().equals("Photography")) {
                            getreq = "&genreid=PHOTOGRAPHY";
                            itemsList.clear();
                        } else if (item.getTitle().toString().equals("Games")) {
                            getreq = "&genreid=GAME";
                            itemsList.clear();
                        } else if (item.getTitle().toString().equals("Music and Audio")) {
                            getreq = "&genreid=MUSIC_AND_AUDIO";
                            itemsList.clear();
                        } else if (item.getTitle().toString().equals("News and Magazines")) {
                            getreq = "&genreid=NEWS_AND_MAGAZINES";
                            itemsList.clear();
                        } else if (item.getTitle().toString().equals("Books and Reference")) {
                            getreq = "&genreid=BOOKS_AND_REFERENCE";
                            itemsList.clear();
                        } else if (item.getTitle().toString().equals("Communication")) {
                            getreq = "&genreid=COMMUNICATION";
                            itemsList.clear();
                        } else if (item.getTitle().toString().equals("Social")) {
                            getreq = "&genreid=SOCIAL";
                            itemsList.clear();
                        } else if (item.getTitle().toString().equals("Education")) {
                            getreq = "&genreid=EDUCATION";
                            itemsList.clear();
                        }
                        loadRecyclerViewData();
                        return true;
                    }
                });
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getreq = "&search="+query;
                itemsList.clear();
                searchView.clearFocus();
                loadRecyclerViewData();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void loadRecyclerViewData() {
        final ProgressDialog progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("updating suggestion list");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url + "?category=" + mycategory +"&request="+getreq,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.e("yeh raha link",url + "?category=" + mycategory +"&request="+getreq);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("details");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                items item;
                                //Log.i("a;fsdk", o.getString("title"));
                                String size = "";
                                if (!(o.getString("size").equals("Varies")))
                                    size = o.getString("size") + "B";
                                else
                                    size = o.getString("size");
                                item = new items(
                                        o.getString("icon"),
                                        o.getInt("id"),
                                        o.getString("title"),
                                        o.getString("installs"),
                                        o.getString("score").substring(0, 3),
                                        size
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
        intent.putExtra("appid", "" + itemsList.get(position).getId());
        startActivity(intent);
    }

    public void savedata() {

        int i = 0;
        for (android.content.pm.PackageInfo packageInfo : listn) {
            if (!((packageInfo.applicationInfo.flags & (packageInfo.applicationInfo.FLAG_UPDATED_SYSTEM_APP |
                    ApplicationInfo.FLAG_SYSTEM)) > 0)) {                // to get user installed apps only.

                names[i] = packageInfo.applicationInfo.loadLabel(getContext().getPackageManager()).toString();
                //Log.e(names[i], format.format(packageInfo.firstInstallTime));
                Log.i(names[i], "name");
                i += 1;
            }
        }

        int temp = 0;
        for (int j = 0; j < names.length; j++) {
            if (names[j] != null)
                temp += 1;
        }
        final String[] namedapps = new String[temp];
        for (int j = 0; j < temp; j++) {
            namedapps[j] = names[j];
        }

        i = 0;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.e("Response", response);
                        Toast.makeText(getContext(), "Account data updated successfully", Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("request", "savedata");
                params.put("userid", userid);
                params.put("totalapps", "" + namedapps.length);
                for (int i = 0; i < namedapps.length; i++) {
                    params.put("appname" + i, names[i]);

                }


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        requestQueue.add(postRequest);
    }

    public void getmycategory() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                urlgetpermissions + "?request=1&userid=" + userid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("permissions");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                Log.e("yeh i permissions",o.getString("Access_Location")+o.getString("Access_Camera"));
                                if (o.getString("Access_Location").equals("0") && o.getString("Access_Camera").equals("0")
                                        && o.getString("Record_Audio").equals("0") && o.getString("Read_phone_state").equals("0")) {
                                    mycategory = "1";
                                } else if ((o.getString("Access_Location").equals("0") || o.getString("Access_Camera").equals("0")
                                        || o.getString("Record_Audio").equals("0") || o.getString("Read_phone_state").equals("0"))
                                        || (o.getString("Read_Contacts").equals("0") && o.getString("Write_Contacts").equals("1")
                                        && o.getString("Read_Messages").equals("1"))) {
                                    mycategory = "2";
                                } else {
                                    mycategory = "3";
                                }
                                myEdit.putString("mycategory", mycategory);
                                myEdit.commit();
                            }

                        } catch (Exception e) {
                            Log.e("error: ", "" + e);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(view.getContext(), "Error! Check your internet", Toast.LENGTH_SHORT).show();

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        requestQueue.add(stringRequest);
    }

}



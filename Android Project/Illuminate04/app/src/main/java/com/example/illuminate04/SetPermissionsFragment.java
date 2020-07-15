package com.example.illuminate04;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class SetPermissionsFragment extends Fragment {


    public SetPermissionsFragment() {
        // Required empty public constructor
    }

    public static final String url = "https://nasfistsolutions.com/illuminate/permissions.php";
    View view;
    private FirebaseAuth mAuth;
    String userid;
    Button savebutton;
    boolean access_location, read_contacts, write_contacts, read_messages, recieve_SMS, recieve_MMS, read_phone_state,
            intercept_Outgoing_calls, modify_phone_state, access_camera, record_audio, read_calender_events, read_browser_history;
    CheckBox Access_Location, Read_Contacts, Write_Contacts, Read_Messages, Recieve_SMS, Recieve_MMS, Read_phone_state,
            Intercept_Outgoing_calls, Modify_phone_state, Access_Camera, Record_Audio, Read_Calender_Events, Read_Browser_History;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_set_permissions, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        userid = mAuth.getCurrentUser().getUid();
        savebutton = view.findViewById(R.id.savebutton);
        Access_Location = view.findViewById(R.id.Access_Location);
        Read_Contacts = view.findViewById(R.id.Read_Contacts);
        Write_Contacts = view.findViewById(R.id.Write_Contacts);
        Read_Messages = view.findViewById(R.id.Read_Messages);
        Recieve_SMS = view.findViewById(R.id.Recieve_SMS);
        Recieve_MMS = view.findViewById(R.id.Recieve_MMS);
        Read_phone_state = view.findViewById(R.id.Read_phone_state);
        Intercept_Outgoing_calls = view.findViewById(R.id.Intercept_Outgoing_calls);
        Modify_phone_state = view.findViewById(R.id.Modify_phone_state);
        Access_Camera = view.findViewById(R.id.Access_Camera);
        Record_Audio = view.findViewById(R.id.Record_Audio);
        Read_Calender_Events = view.findViewById(R.id.Read_Calender_Events);
        Read_Browser_History = view.findViewById(R.id.Read_Browser_History);

        sharedPreferences = this.getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        myEdit = sharedPreferences.edit();


        getpermissionsdata(); //method to get permissions data of user from database

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myEdit.putString("mycategory","");
                myEdit.commit();
                /*Log.i("check variables:",""+access_location+" "+read_contacts+" "+ write_contacts+" "+ read_messages+" "+
                        recieve_SMS+" "+ recieve_MMS+" "+ read_phone_state+" "+
                        intercept_Outgoing_calls+" "+ modify_phone_state+" "+ access_camera+" "+ record_audio+" "+read_calender_events
                        +" "+read_browser_history);*/

                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Log.d("Response", response);
                                Toast.makeText(getContext(),"permissions updated",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getActivity(),Home.class));
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
                        params.put("request", "2");
                        params.put("userid", userid);
                        params.put("Access_Location", "" + access_location);
                        params.put("Read_Contacts", "" + read_contacts);
                        params.put("Write_Contacts", "" + write_contacts);
                        params.put("Read_Messages", "" + read_messages);
                        params.put("Recieve_SMS", "" + recieve_SMS);
                        params.put("Recieve_MMS", "" + recieve_MMS);
                        params.put("Read_phone_state", "" + read_phone_state);
                        params.put("Intercept_Outgoing_calls", "" + intercept_Outgoing_calls);
                        params.put("Modify_phone_state", "" + modify_phone_state);
                        params.put("Access_Camera", "" + access_camera);
                        params.put("Record_Audio", "" + record_audio);
                        params.put("Read_Calender_Events", "" + read_calender_events);
                        params.put("Read_Browser_History", "" + read_browser_history);

                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(postRequest);

            }
        });

        Access_Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access_location) {
                    access_location = false;
                } else {
                    access_location = true;
                }
            }
        });
        Read_Contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (read_contacts) {
                    read_contacts = false;
                } else {
                    read_contacts = true;
                }
            }
        });
        Write_Contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (write_contacts) {
                    write_contacts = false;
                } else {
                    write_contacts = true;
                }
            }
        });
        Read_Messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (read_messages) {
                    read_messages = false;
                } else {
                    read_messages = true;
                }
            }
        });

        Recieve_SMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recieve_SMS) {
                    recieve_SMS = false;
                } else {
                    recieve_SMS = true;
                }
            }
        });

        Recieve_MMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recieve_MMS) {
                    recieve_MMS = false;
                } else {
                    recieve_MMS = true;
                }
            }
        });

        Read_phone_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (read_phone_state) {
                    read_phone_state = false;
                } else {
                    read_phone_state = true;
                }
            }
        });
        Intercept_Outgoing_calls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intercept_Outgoing_calls) {
                    intercept_Outgoing_calls = false;
                } else {
                    intercept_Outgoing_calls = true;
                }
            }
        });
        Modify_phone_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (modify_phone_state) {
                    modify_phone_state = false;
                } else {
                    modify_phone_state = true;
                }
            }
        });
        Access_Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access_camera) {
                    access_camera = false;
                } else {
                    access_camera = true;
                }
            }
        });
        Record_Audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (record_audio) {
                    record_audio = false;
                } else {
                    record_audio = true;
                }
            }
        });
        Read_Calender_Events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (read_calender_events) {
                    read_calender_events = false;
                } else {
                    read_calender_events = true;
                }
            }
        });
        Read_Browser_History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (read_browser_history) {
                    read_browser_history = false;
                } else {
                    read_browser_history = true;
                }
            }
        });
    }

    public void getpermissionsdata() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url + "?request=1&userid=" + userid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("permissions");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                if (o.getString("Access_Location").equals("1")) {
                                    access_location = true;
                                    Access_Location.setChecked(true);
                                } else
                                    access_location = false;
                                if (o.getString("Read_Contacts").equals("1")) {
                                    read_contacts = true;
                                    Read_Contacts.setChecked(true);
                                } else
                                    read_contacts = false;
                                if (o.getString("Write_Contacts").equals("1")) {
                                    write_contacts = true;
                                    Write_Contacts.setChecked(true);
                                } else
                                    write_contacts = false;
                                if (o.getString("Read_Messages").equals("1")) {
                                    read_messages = true;
                                    Read_Messages.setChecked(true);
                                } else
                                    read_messages = false;
                                if (o.getString("Recieve_SMS").equals("1")) {
                                    recieve_SMS = true;
                                    Recieve_SMS.setChecked(true);
                                } else recieve_SMS = false;
                                if (o.getString("Recieve_MMS").equals("1")) {
                                    recieve_MMS = true;
                                    Recieve_MMS.setChecked(true);
                                } else
                                    recieve_MMS = false;
                                if (o.getString("Read_phone_state").equals("1")) {
                                    read_phone_state = true;
                                    Read_phone_state.setChecked(true);
                                } else
                                    read_phone_state = false;
                                if (o.getString("Intercept_Outgoing_calls").equals("1")) {
                                    intercept_Outgoing_calls = true;
                                    Intercept_Outgoing_calls.setChecked(true);
                                } else
                                    intercept_Outgoing_calls = false;
                                if (o.getString("Modify_phone_state").equals("1")) {
                                    modify_phone_state = true;
                                    Modify_phone_state.setChecked(true);
                                } else
                                    modify_phone_state = false;
                                if (o.getString("Access_Camera").equals("1")) {
                                    access_camera = true;
                                    Access_Camera.setChecked(true);
                                } else
                                    access_camera = false;
                                if (o.getString("Record_Audio").equals("1")) {
                                    record_audio = true;
                                    Record_Audio.setChecked(true);
                                } else
                                    record_audio = false;
                                if (o.getString("Read_Calender_Events").equals("1")) {
                                    read_calender_events = true;
                                    Read_Calender_Events.setChecked(true);
                                } else
                                    read_calender_events = false;
                                if (o.getString("Read_Browser_History").equals("1")) {
                                    read_browser_history = true;
                                    Read_Browser_History.setChecked(true);
                                } else
                                    read_browser_history = false;
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

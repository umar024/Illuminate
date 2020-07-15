package com.example.illuminate04;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    public View view;
    static Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;
    private FirebaseAuth mAuth;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        getActivity().getFragmentManager().beginTransaction()
                .replace(R.id.container, new MySettingsFragment())
                .commit();
    }


    public SettingsFragment() {
        // Required empty public constructor
    }

    // method to clear shared preferences on logout
    public void clearSP(Context context) {
        sharedPreferences = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        myEdit = sharedPreferences.edit();

        myEdit.clear();
        myEdit.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_settings, container, false);
        context = view.getContext();


       /*
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.KEYCODE_BACK)
                    return true;
                return false;
            }
        });*/


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    public static class MySettingsFragment extends PreferenceFragment {
        public MySettingsFragment() {
        }
        private FirebaseAuth mAuth;
        SettingsFragment sf = new SettingsFragment();

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            mAuth = FirebaseAuth.getInstance();
            addPreferencesFromResource(R.xml.preferences);
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        }


        @Override
        public boolean onPreferenceTreeClick(Preference preference) {

            String key = preference.getKey();
            if (key.equals("editpermissions")) {
                //startActivity(new Intent(context, SetPermissionsFragment.class));
                Log.e("clicked:", key);
            } else if (key.equals("Logout")) {

                    new AlertDialog.Builder(context)
                            .setTitle("Logout?")
                            .setMessage("Are you sure you want to Logout?")
                            .setNegativeButton(android.R.string.no, null)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface arg0, int arg1) {
                                    mAuth.signOut();
                                    sf.clearSP(context);
                                    getActivity().finishAffinity();
                                    startActivity(new Intent(context, MainActivity.class));
                                }
                            }).create().show();
            }
            return super.onPreferenceTreeClick(preference);
        }


    }


}

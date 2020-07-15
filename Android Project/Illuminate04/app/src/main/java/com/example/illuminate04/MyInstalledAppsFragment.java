package com.example.illuminate04;


import android.app.ProgressDialog;
import android.app.usage.StorageStats;
import android.app.usage.StorageStatsManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
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

import java.io.File;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import static android.content.pm.ApplicationInfo.FLAG_SYSTEM;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyInstalledAppsFragment extends Fragment {


    public MyInstalledAppsFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    InstalledItemAdapter adapter;
    List<installeditem> itemsList;
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_installed_apps, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itemsList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        loadRecyclerViewData();
    }

    public void loadRecyclerViewData() {
//        final ProgressDialog progressDialog = new ProgressDialog(view.getContext());
//        progressDialog.setMessage("updating suggestion list");
//        progressDialog.show();

        List<android.content.pm.PackageInfo> listn = view.getContext().getPackageManager().getInstalledPackages(0);
        String[] names = new String[listn.size()];
        int i = 0;
        HashMap<String, Drawable> _item = new HashMap<String, android.graphics.drawable.Drawable>();
        installeditem item;
        File file;
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        for (android.content.pm.PackageInfo packageInfo : listn) {
            if (!((packageInfo.applicationInfo.flags & (packageInfo.applicationInfo.FLAG_UPDATED_SYSTEM_APP |
                    ApplicationInfo.FLAG_SYSTEM)) > 0)) {                // to get user installed apps only.


                names[i] = packageInfo.applicationInfo.loadLabel(view.getContext().getPackageManager()).toString();
                //Log.e(names[i], format.format(packageInfo.firstInstallTime));


                file = new File(packageInfo.applicationInfo.publicSourceDir);

                int size = (int) file.length();


                Log.i(names[i], file.toString());


                _item.put("itm", packageInfo.applicationInfo.loadIcon(view.getContext().getPackageManager()));
                item = new installeditem(packageInfo.applicationInfo.loadIcon(view.getContext().getPackageManager()),
                        names[i],
                        size+"",
                        packageInfo.versionName,
                        format.format(packageInfo.firstInstallTime));
                itemsList.add(item);
                i += 1;
            }
        }

        adapter = new InstalledItemAdapter(view.getContext(),itemsList);
        recyclerView.setAdapter(adapter);

    }
}

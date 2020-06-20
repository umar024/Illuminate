package com.example.illuminate04;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InstalledItemAdapter extends RecyclerView.Adapter<InstalledItemAdapter.itemVeiwHolder> {

    private Context mCtx;
    private List<installeditem> itemsList;


    public InstalledItemAdapter(Context mCtx, List<installeditem> itemsList) {
        this.mCtx = mCtx;
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public itemVeiwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.installed_list_item, null);
        return new itemVeiwHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull itemVeiwHolder holder, int position) {
        installeditem item = itemsList.get(position);

        holder.textViewtitile.setText(item.getAppname());
        holder.textViewsize.setText(item.getSize());
        holder.textViewversion.setText(item.getVersion());
        holder.textViewreInstalledOn.setText(item.getInstalledOn());

        holder.imageView.setImageDrawable(item.getImage());


    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    class itemVeiwHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewtitile, textViewversion, textViewreInstalledOn, textViewsize;

        public itemVeiwHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textViewversion = itemView.findViewById(R.id.textViewVersion);
            textViewtitile = itemView.findViewById(R.id.textViewTitle);
            textViewsize = itemView.findViewById(R.id.textViewsize);
            textViewreInstalledOn = itemView.findViewById(R.id.textViewInstalledOn);
        }
    }
}

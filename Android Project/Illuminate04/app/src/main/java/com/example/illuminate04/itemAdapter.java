package com.example.illuminate04;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class itemAdapter extends RecyclerView.Adapter<itemAdapter.itemVeiwHolder> {

    private Context mCtx;
    private List<items> itemsList;
    private OnAppListener MonAppListener;


    public itemAdapter(Context mCtx, List<items> itemsList, OnAppListener onAppListener) {
        this.mCtx = mCtx;
        this.itemsList = itemsList;
        this.MonAppListener = onAppListener;
    }

    @NonNull
    @Override
    public itemVeiwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_item, null);
        return new itemVeiwHolder(view, MonAppListener);

    }

    @Override
    public void onBindViewHolder(@NonNull itemVeiwHolder holder, int position) {
        items item = itemsList.get(position);

        holder.textViewtitile.setText(item.getAppname());
        holder.textViewInstalls.setText(item.getInstalls());
        holder.textViewScore.setText(item.getScore());
        holder.textViewSize.setText(item.getSize());
        //holder.imageView.setImageDrawable(item.getImage());
        Picasso.get().load(item.getImage()).into(holder.imageView);

        //holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(item.getImage(),null));


    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    class itemVeiwHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView textViewtitile, textViewInstalls, textViewScore, textViewSize;
        OnAppListener onAppListener;


        public itemVeiwHolder(@NonNull View itemView, OnAppListener onAppListener) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textViewtitile = itemView.findViewById(R.id.textViewTitle);
            textViewInstalls = itemView.findViewById(R.id.textViewInstalls);
            textViewScore = itemView.findViewById(R.id.textViewScore);
            textViewSize = itemView.findViewById(R.id.textViewSize);

            this.onAppListener = onAppListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onAppListener.onAppClick(getAdapterPosition());
        }
    }

    public interface OnAppListener {
        void onAppClick(int position);
    }
}

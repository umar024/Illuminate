package com.example.illuminate00;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class itemAdapter extends RecyclerView.Adapter<itemAdapter.itemVeiwHolder> {

    private Context mCtx;
    private List<items> itemsList;

    public itemAdapter(Context mCtx, List<items> itemsList) {
        this.mCtx = mCtx;
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public itemVeiwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_item, null);
        return new itemVeiwHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull itemVeiwHolder holder, int position) {
        items item = itemsList.get(position);

        holder.textViewtitile.setText(item.getAppname());
        holder.textViewreview.setText(String.valueOf(item.getReviews()));
        holder.textViewrating.setText(item.getRating());
        holder.textViewsize.setText(item.getSize());

        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(item.getImage(),null));


    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    class itemVeiwHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewtitile, textViewrating, textViewreview, textViewsize;

        public itemVeiwHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textViewtitile = itemView.findViewById(R.id.textViewTitle);
            textViewrating = itemView.findViewById(R.id.textViewRating);
            textViewreview = itemView.findViewById(R.id.textViewReview);
            textViewsize = itemView.findViewById(R.id.textViewsize);
        }
    }
}

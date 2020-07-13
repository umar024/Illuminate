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

public class imageAdapter extends RecyclerView.Adapter<imageAdapter.itemVeiwHolder> {

    private Context mCtx;
    private List<image> imagesList;

    public imageAdapter(Context context, List<image> imagesList){
        this.mCtx = context;
        this.imagesList = imagesList;
    }


    @NonNull
    @Override
    public itemVeiwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.screenshot_list, null);
        return new itemVeiwHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull itemVeiwHolder holder, int position) {
        image image = imagesList.get(position);
        Picasso.get().load(image.getImage()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }


    class itemVeiwHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public itemVeiwHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.ScreenShot);
        }
    }
}

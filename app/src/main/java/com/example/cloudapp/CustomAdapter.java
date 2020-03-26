package com.example.cloudapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;


import java.util.List;

class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder>{
    private Context context;
    private List<Integer> images;

    public CustomAdapter( Context mainActivity, List<Integer> photos) {
        context=mainActivity;
        images=photos;
    }

    @Override
    public CustomViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.photo,parent,false);

        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }


    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Integer image=images.get(position);

        Glide.with(context).load(image).centerCrop().into(holder.image);



        //Picasso.with(context).load(image).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}
class CustomViewHolder extends RecyclerView.ViewHolder{
    ImageView image;
    CardView card;
    public CustomViewHolder(View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.poster);

    }
}

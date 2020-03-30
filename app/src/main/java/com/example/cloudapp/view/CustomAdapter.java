package com.example.cloudapp.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.cloudapp.R;
import com.example.cloudapp.model.ImagePath;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder>{
    private Context context;
    private List<ImagePath> images;

    public CustomAdapter( Context mainActivity, List<ImagePath> photos) {
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
        ImagePath image=images.get(position);
        holder.setPath(image);
        File d=new File(image.getPath());
        Uri dd=Uri.fromFile(d);
        Glide.with(context).load(dd).centerCrop().into(holder.image);


        //Picasso.with(context).load(image).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}



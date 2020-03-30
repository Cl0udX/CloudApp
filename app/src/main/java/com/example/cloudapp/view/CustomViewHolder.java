package com.example.cloudapp.view;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.example.cloudapp.R;
import com.example.cloudapp.model.ImagePath;

import org.json.JSONObject;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView image;
    CardView card;
    ImagePath path;
    JSONObject json;
    public CustomViewHolder(View itemView)  {
        super(itemView);
        image = itemView.findViewById(R.id.poster);
        itemView.setOnClickListener(this);
    }
    public void setPath(ImagePath path){
        this.path=(path);
    }


    @Override
    public void onClick(View v) {

        Intent i=new Intent(v.getContext(),ImageActivity.class);

        i.putExtra("path",path.getPath());
        v.getContext().startActivity(i);
    }

}
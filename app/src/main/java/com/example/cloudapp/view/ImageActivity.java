package com.example.cloudapp.view;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.cloudapp.R;

import java.io.File;
import java.io.Serializable;

public class ImageActivity extends AppCompatActivity {

    private Button backImage;
    private ImageView photo;
    private TextView smile;
    private TextView gender;
    private TextView rageAge;
    private TextView emotion0;
    private TextView emotion0v;
    private TextView emotion1;
    private TextView emotion1v;
    private TextView emotion2;
    private TextView emotion2v;
    private TextView emotion3;
    private TextView emotion3v;
    private TextView emotion4;
    private TextView emotion4v;
    private TextView emotion5;
    private TextView emotion5v;
    private TextView emotion6;
    private TextView emotion6v;
    private TextView emotion7;
    private TextView emotion7v;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_image);
        backImage=findViewById(R.id.backImage);
        photo=findViewById(R.id.photoImage);
        smile=findViewById(R.id.smile);
        rageAge=findViewById(R.id.rangeage);
        gender=findViewById(R.id.gender);
        emotion0=findViewById(R.id.emotion0);
        emotion0v=findViewById(R.id.emotion0v);
        emotion1=findViewById(R.id.emotion1);
        emotion1v=findViewById(R.id.emotion1v);
        emotion2=findViewById(R.id.emotion2);
        emotion2v=findViewById(R.id.emotion2v);
        emotion3=findViewById(R.id.emotion3);
        emotion3v=findViewById(R.id.emotion3v);
        emotion4=findViewById(R.id.emotion4);
        emotion4v=findViewById(R.id.emotion4v);
        emotion5=findViewById(R.id.emotion5);
        emotion5v=findViewById(R.id.emotion5v);
        emotion6=findViewById(R.id.emotion6);
        emotion6v=findViewById(R.id.emotion6v);
        emotion7=findViewById(R.id.emotion7);
        emotion7v=findViewById(R.id.emotion7v);
        Serializable out=getIntent().getExtras().getSerializable("path");
        if(out!=null){
            File d=new File((String)out);
            Uri dd=Uri.fromFile(d);
            Glide.with(this).load(d).into(photo);
        }
        backImage.setOnClickListener(
                (v)->{
                    Intent bac=new Intent();
                    finish();
                }
        );

    }
}

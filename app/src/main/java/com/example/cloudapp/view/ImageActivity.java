package com.example.cloudapp.view;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.cloudapp.R;
import com.example.cloudapp.model.AWSS3Connection;
import com.example.cloudapp.model.AsyncCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        final Serializable out=getIntent().getExtras().getSerializable("path");

        final File imageFile = new File((String)out);
        String[] imageData = imageFile.getName().replace(" ", "_").replace(".", ",").split(",");
        final String imageName = imageData[0];
        final String imageFormat = imageData[1];

        Log.i("OnCreate ImageActivity", "onCreate: imgName"+imageName);
        //load image
        AWSS3Connection conn = AWSS3Connection.getAWSS3Connection(this.getApplicationContext());


        new Thread(new Runnable(){
            @Override
            public void run() {

                conn.upload("images/"+imageName+"."+imageFormat, imageFile, new AsyncCallback<String>() {
                    @Override
                    public void callback(String data) {
                        try {
                            Thread.currentThread().sleep(240000);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        if(data.equals("200"))

                            conn.download("jsons/" + imageName + ".json", new AsyncCallback<File>() {
                                @Override
                                public void callback(File data) {
                                    Log.i("download callback", "callback: ");
                                    if(data!=null){
                                        try {
                                            Log.i("download callback", "callback: not null ");

                                            FileReader fr = new FileReader(data);
                                            BufferedReader br = new BufferedReader(fr);
                                            String dataJson = "";
                                            String line = null;
                                            while(( line = br.readLine())!=null && line!=null && !line.isEmpty()){
                                                dataJson+=line;
                                            }

                                            JSONObject object= new JSONObject(dataJson);
                                            readJason(object);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                @Override
                                public void error(Exception e) {
                                    e.printStackTrace();
                                }
                            });
                    }

                    @Override
                    public void error(Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }).start();

        //end load


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
    public void readJason(JSONObject object) throws Exception {
        JSONObject faceDetail=object.getJSONArray("FaceDetails").getJSONObject(0);
        //----------------------------*************************-----------------------------
        JSONObject ageRange=faceDetail.getJSONObject("AgeRange");
        int low=ageRange.getInt("Low");
        int high=ageRange.getInt("High");
        rageAge.setText("[ "+low+" : "+high+" ]");
        //----------------------------*************************-----------------------------
        JSONObject smile=faceDetail.getJSONObject("Smile");
        Boolean smileV=smile.getBoolean("Value");
        double smileC=smile.getDouble("Confidence");
        this.smile.setText(smileV+" con una precicion del: "+Math.ceil(smileC)+"%");
        //----------------------------*************************-----------------------------
        JSONObject gender=faceDetail.getJSONObject("Gender");
        String genderV=gender.getString("Value");
        double genferC=gender.getDouble("Confidence");
        this.gender.setText(genderV+" con una precicion del: "+Math.ceil(genferC)+"%");
        //----------------------------*************************-----------------------------
        JSONArray emotions=faceDetail.getJSONArray("Emotions");
        JSONObject emotion0=emotions.getJSONObject(0);
        JSONObject emotion1=emotions.getJSONObject(1);
        JSONObject emotion2=emotions.getJSONObject(2);
        JSONObject emotion3=emotions.getJSONObject(3);
        JSONObject emotion4=emotions.getJSONObject(4);
        JSONObject emotion5=emotions.getJSONObject(5);
        JSONObject emotion6=emotions.getJSONObject(6);
        JSONObject emotion7=emotions.getJSONObject(7);
        String emotion0t=emotion0.getString("Type");
        Double emotion0v=emotion0.getDouble("Confidence");
        String emotion1t=emotion1.getString("Type");
        Double emotion1v=emotion1.getDouble("Confidence");
        String emotion2t=emotion2.getString("Type");
        Double emotion2v=emotion2.getDouble("Confidence");
        String emotion3t=emotion3.getString("Type");
        Double emotion3v=emotion3.getDouble("Confidence");
        String emotion4t=emotion4.getString("Type");
        Double emotion4v=emotion4.getDouble("Confidence");
        String emotion5t=emotion5.getString("Type");
        Double emotion5v=emotion5.getDouble("Confidence");
        String emotion6t=emotion6.getString("Type");
        Double emotion6v=emotion6.getDouble("Confidence");
        String emotion7t=emotion7.getString("Type");
        Double emotion7v=emotion7.getDouble("Confidence");
        this.emotion0.setText(emotion0t);
        this.emotion0v.setText(Math.ceil(emotion0v)+"%");
        this.emotion1.setText(emotion1t);
        this.emotion1v.setText(Math.ceil(emotion1v)+"%");
        this.emotion2.setText(emotion2t);
        this.emotion2v.setText(Math.ceil(emotion2v)+"%");
        this.emotion3.setText(emotion3t);
        this.emotion3v.setText(Math.ceil(emotion3v)+"%");
        this.emotion4.setText(emotion4t);
        this.emotion4v.setText(Math.ceil(emotion4v)+"%");
        this.emotion5.setText(emotion5t);
        this.emotion5v.setText(Math.ceil(emotion5v)+"%");
        this.emotion6.setText(emotion6t);
        this.emotion6v.setText(Math.ceil(emotion6v)+"%");
        this.emotion7.setText(emotion7t);
        this.emotion7v.setText(Math.ceil(emotion7v)+"%");
    }
}

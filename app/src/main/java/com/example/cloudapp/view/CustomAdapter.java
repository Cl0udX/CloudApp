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
class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView image;
    CardView card;
    ImagePath path;
    JSONObject json;
    public CustomViewHolder(View itemView)  {
        super(itemView);
        //try {
            //json=new JSONObject("{\"FaceDetails\":[{\"BoundingBox\":{\"Width\":0.2713003158569336,\"Height\":0.5386383533477783,\"Left\":0.3325428366661072,\"Top\":0.18552331626415253},\"AgeRange\":{\"Low\":22,\"High\":34},\"Smile\":{\"Value\":true,\"Confidence\":96.78943634033203},\"Eyeglasses\":{\"Value\":false,\"Confidence\":99.46656036376953},\"Sunglasses\":{\"Value\":false,\"Confidence\":99.82221221923828},\"Gender\":{\"Value\":\"Male\",\"Confidence\":99.62586975097656},\"Beard\":{\"Value\":true,\"Confidence\":88.71471405029297},\"Mustache\":{\"Value\":false,\"Confidence\":69.57037353515625},\"EyesOpen\":{\"Value\":true,\"Confidence\":99.3416748046875},\"MouthOpen\":{\"Value\":true,\"Confidence\":98.61060333251953},\"Emotions\":[{\"Type\":\"FEAR\",\"Confidence\":0.7951542735099792},{\"Type\":\"DISGUSTED\",\"Confidence\":2.2294516563415527},{\"Type\":\"ANGRY\",\"Confidence\":1.975245714187622},{\"Type\":\"HAPPY\",\"Confidence\":90.68866729736328},{\"Type\":\"SAD\",\"Confidence\":0.17777656018733978},{\"Type\":\"SURPRISED\",\"Confidence\":1.6850570440292358},{\"Type\":\"CONFUSED\",\"Confidence\":1.3534514904022217},{\"Type\":\"CALM\",\"Confidence\":1.095202088356018}],\"Landmarks\":[{\"Type\":\"eyeLeft\",\"X\":0.39344850182533264,\"Y\":0.39141443371772766},{\"Type\":\"eyeRight\",\"X\":0.5225653648376465,\"Y\":0.3786851465702057},{\"Type\":\"mouthLeft\",\"X\":0.40694430470466614,\"Y\":0.5631638169288635},{\"Type\":\"mouthRight\",\"X\":0.513668417930603,\"Y\":0.5525542497634888},{\"Type\":\"nose\",\"X\":0.4454672336578369,\"Y\":0.4483657479286194},{\"Type\":\"leftEyeBrowLeft\",\"X\":0.34751152992248535,\"Y\":0.36383649706840515},{\"Type\":\"leftEyeBrowRight\",\"X\":0.4133584499359131,\"Y\":0.32582661509513855},{\"Type\":\"leftEyeBrowUp\",\"X\":0.3782412111759186,\"Y\":0.325670063495636},{\"Type\":\"rightEyeBrowLeft\",\"X\":0.48956748843193054,\"Y\":0.31808432936668396},{\"Type\":\"rightEyeBrowRight\",\"X\":0.5775725841522217,\"Y\":0.34350159764289856},{\"Type\":\"rightEyeBrowUp\",\"X\":0.5307468175888062,\"Y\":0.3109962046146393},{\"Type\":\"leftEyeLeft\",\"X\":0.3736814558506012,\"Y\":0.39525070786476135},{\"Type\":\"leftEyeRight\",\"X\":0.4188063144683838,\"Y\":0.3893516957759857},{\"Type\":\"leftEyeUp\",\"X\":0.3928510844707489,\"Y\":0.3803136944770813},{\"Type\":\"leftEyeDown\",\"X\":0.3945843577384949,\"Y\":0.39684805274009705},{\"Type\":\"rightEyeLeft\",\"X\":0.49517112970352173,\"Y\":0.38209402561187744},{\"Type\":\"rightEyeRight\",\"X\":0.5441598892211914,\"Y\":0.37953078746795654},{\"Type\":\"rightEyeUp\",\"X\":0.5193261504173279,\"Y\":0.3686235249042511},{\"Type\":\"rightEyeDown\",\"X\":0.5191321969032288,\"Y\":0.38545456528663635},{\"Type\":\"noseLeft\",\"X\":0.4289930760860443,\"Y\":0.48715779185295105},{\"Type\":\"noseRight\",\"X\":0.4782545566558838,\"Y\":0.4837041199207306},{\"Type\":\"mouthUp\",\"X\":0.45355328917503357,\"Y\":0.5240607857704163},{\"Type\":\"mouthDown\",\"X\":0.45685654878616333,\"Y\":0.5803918838500977},{\"Type\":\"leftPupil\",\"X\":0.39344850182533264,\"Y\":0.39141443371772766},{\"Type\":\"rightPupil\",\"X\":0.5225653648376465,\"Y\":0.3786851465702057},{\"Type\":\"upperJawlineLeft\",\"X\":0.33936965465545654,\"Y\":0.45262205600738525},{\"Type\":\"midJawlineLeft\",\"X\":0.3684101104736328,\"Y\":0.6307134032249451},{\"Type\":\"chinBottom\",\"X\":0.46625635027885437,\"Y\":0.6851509213447571},{\"Type\":\"midJawlineRight\",\"X\":0.5986204743385315,\"Y\":0.6094663739204407},{\"Type\":\"upperJawlineRight\",\"X\":0.6261888742446899,\"Y\":0.4259241819381714}],\"Pose\":{\"Roll\":-4.97549295425415,\"Yaw\":-2.904961347579956,\"Pitch\":20.263965606689453},\"Quality\":{\"Brightness\":79.75887298583984,\"Sharpness\":94.08262634277344},\"Confidence\":99.99998474121094}]}");
        //}catch (Exception E){

       // }
        image = itemView.findViewById(R.id.poster);
        itemView.setOnClickListener(this);
    }
    public void setPath(ImagePath path){
        this.path=(path);
    }
    public void setJson(JSONObject json){
        this.json=json;
    }
    @Override
    public void onClick(View v) {
        Intent i=new Intent(v.getContext(),ImageActivity.class);
        i.putExtra("path",path.getPath());
        i.putExtra("json",json.toString());
        v.getContext().startActivity(i);
    }

}

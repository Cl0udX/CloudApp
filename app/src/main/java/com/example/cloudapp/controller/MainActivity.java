package com.example.cloudapp.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.Manifest;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.example.cloudapp.R;
import com.example.cloudapp.view.CustomAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSONS_CALLBACK = 1;
    private ArrayList<imagePath> folds;
    private ArrayList<imagePath> complete;
    private int part;
    private RecyclerView recyclerView;
    private EndlessRecyclerViewScrollListener scrollListener;
    private CustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        part=0;
        setContentView(R.layout.photos_viewer);

        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.ACCESS_MEDIA_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE

        }, PERMISSONS_CALLBACK);
        showPhotos();

    }

    private void showPhotos() {
        recyclerView= findViewById(R.id.grid);
        adapter=new CustomAdapter(this,folds);
        recyclerView.setAdapter(adapter);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                int left = outRect.left;
                int right = outRect.right;
                int top = outRect.top;
                int bottom = outRect.bottom;
                int idx = parent.getChildPosition(view);
                int perRow = layoutManager.getSpanCount();

                int adj = 150; // some adjustment

                    if(idx % 2 == perRow - 1){
                        // on last column, adjust. Right magically adjusts bottom, so adjust it too...
                        top += adj;
                    }

                    outRect.set(left, top, right, bottom);
                }

                outRect.set(left, top, right, bottom);
            }
        });
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };
        complete=getPicturePaths();
        folds=new ArrayList<imagePath>();
        recyclerView.addOnScrollListener(scrollListener);

    }
    public void loadNextDataFromApi(int page){
        folds.clear();
        int pa=page+1;
        int j=(pa*100)-100;
        for (int i=0;i<100;i++,j++){
            folds.add(complete.get(j));
        }
        adapter.notifyDataSetChanged();
        scrollListener.resetState();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==PERMISSONS_CALLBACK){
            showPhotos();
        }
    }
    private ArrayList<imagePath> getPicturePaths(){
        int limit=100;
        ArrayList<imagePath> picFolders = new ArrayList<>();
        ArrayList<String> picPaths = new ArrayList<>();
        Uri allImagesuri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.Images.ImageColumns.DATA ,MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,MediaStore.Images.Media.BUCKET_ID};
        Cursor cursor = this.getContentResolver().query(allImagesuri, projection, null, null, null);
        try {
            if (cursor != null) {
                cursor.moveToFirst();
            }
            do{
                imagePath folds = new imagePath();
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                String folder = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                String datapath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

                //String folderpaths =  datapath.replace(name,"");
                //String folderpaths = datapath.substring(0, datapath.lastIndexOf(folder+"/"));
               // folderpaths = folderpaths+folder+"/";
                //if (!picPaths.contains(folderpaths)) {
               //     picPaths.add(folderpaths);
               //
               // }
                folds.setPath(datapath);
                picFolders.add(folds);
            }while(cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //for(int i = 0;i < picFolders.size();i++){
        //    Log.d("picture folders","path = "+picFolders.get(i).getPath());
        //}
        return picFolders;
    }
}

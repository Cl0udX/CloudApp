package com.example.cloudapp.controller;

import android.Manifest;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.example.cloudapp.EndlessRecyclerViewScrollListener;
import com.example.cloudapp.R;
import com.example.cloudapp.view.CustomAdapter;
import com.example.cloudapp.model.ImagePath;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSONS_CALLBACK = 1;
    private ArrayList<ImagePath> folds;
    private ArrayList<ImagePath> complete;
    private int part;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        part = 0;
        setContentView(R.layout.photos_viewer);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_MEDIA_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE

        }, PERMISSONS_CALLBACK);

        showPhotos();

    }



    private void showPhotos() {


        recyclerView = findViewById(R.id.grid);
        complete = getPicturePaths();
        folds = new ArrayList<>();
        for (int i = 0; i <= 20 && i < complete.size(); i++) {
            folds.add(complete.get(i));

        }

        adapter = new CustomAdapter(this, folds);
        recyclerView.setAdapter(adapter);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                Log.i("INFO", "onLoadMore: "+page+" t: "+totalItemsCount);
                loadNextData(page);

            }
        };
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

                if (idx == 1) {
                    // on last column, adjust. Right magically adjusts bottom, so adjust it too...
                    top += adj;
                }

                outRect.set(left, top, right, bottom);
            }
        });
        recyclerView.addOnScrollListener(scrollListener);

    }


    public void loadNextData(int page) {
        folds.clear();
        int pa = page;
        int i = ( (pa-1) * 20);
        for (; i<i+20 && i < complete.size(); i++) {
            folds.add(complete.get(i));
        }
        adapter.notifyDataSetChanged();
        scrollListener.resetState();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSONS_CALLBACK) {
            showPhotos();
        }
    }

    private ArrayList<ImagePath> getPicturePaths() {
        int limit = 200;
        ArrayList<ImagePath> picFolders = new ArrayList<>();
        ArrayList<String> picPaths = new ArrayList<>();
        Uri allImagesuri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.BUCKET_ID};
        Cursor cursor = this.getContentResolver().query(allImagesuri, projection, null, null, null);
        try {
            if (cursor != null) {
                cursor.moveToFirst();
            }
            do {
                ImagePath folds = new ImagePath();
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                String folder = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                String datapath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));


                folds.setPath(datapath);
                picFolders.add(folds);
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return picFolders;
    }
}

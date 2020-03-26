package com.example.cloudapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Integer> photos;
    private CustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.photos_viewer);
        recyclerView= findViewById(R.id.grid);
        photos=new ArrayList<>();
        adapter=new CustomAdapter(this,photos);
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

                    int adj = 50; // some adjustment


                    if(idx % 2 == perRow - 1){
                        // on last column, adjust. Right magically adjusts bottom, so adjust it too...
                        top += adj;
                    }

                    outRect.set(left, top, right, bottom);
                }
        });

        photos.add(R.drawable.e1);
        photos.add(R.drawable.e2);
        photos.add(R.drawable.e3);
        photos.add(R.drawable.e4);
        photos.add(R.drawable.e5);
        photos.add(R.drawable.e6);
        photos.add(R.drawable.e7);
        photos.add(R.drawable.e8);
        photos.add(R.drawable.e9);
        adapter.notifyDataSetChanged();

    }

}

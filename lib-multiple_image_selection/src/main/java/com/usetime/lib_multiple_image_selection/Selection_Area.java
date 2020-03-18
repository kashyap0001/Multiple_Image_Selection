package com.usetime.lib_multiple_image_selection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.usetime.lib_multiple_image_selection.Adapter.Rv_SelectionImages;

import java.util.ArrayList;

public class Selection_Area extends AppCompatActivity implements SendRemoveMessage {

    ArrayList<String> arl_imglist;

    RecyclerView rv_imgs;

    TextView tv_scount;

    public static ArrayList<String> selected = new ArrayList<>();

    DisplayMetrics matrix = new DisplayMetrics();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_selection__area);

        selected.clear();

        getWindowManager().getDefaultDisplay().getMetrics(matrix);

        arl_imglist = getIntent().getStringArrayListExtra("img_list");

        tv_scount = findViewById(R.id.tv_selectedcount);
        setTextForcount();
        rv_imgs = findViewById(R.id.rv_imgs);

        rv_imgs.setAdapter(new Rv_SelectionImages(arl_imglist, matrix, this));
        rv_imgs.setLayoutManager(new GridLayoutManager(Selection_Area.this, getcount()));

    }

    void setTextForcount() {
        tv_scount.setText("Current selected images : " + selected.size());
    }

    public void done(View view) {
        for (String name : selected) {
            if (!Select_multiple.Selected_Images.contains(name)) {
                Select_multiple.Selected_Images.add(name);
            }
        }
        finish();
    }

    public void back(View view) {
        onBackPressed();
    }

    @Override
    public void Recreate() {
        setTextForcount();
    }

   public int getcount(){
       float scalefactor = getResources().getDisplayMetrics().density * 100;
       int number = getWindowManager().getDefaultDisplay().getWidth();
       int columns = (int) ((float) number / (float) scalefactor);

       return columns;
   }
}

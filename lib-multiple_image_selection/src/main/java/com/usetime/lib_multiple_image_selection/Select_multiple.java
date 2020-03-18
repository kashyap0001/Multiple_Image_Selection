package com.usetime.lib_multiple_image_selection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.usetime.lib_multiple_image_selection.Adapter.Rv_BucketList;
import com.usetime.lib_multiple_image_selection.Adapter.Rv_selectedImage;

import java.util.ArrayList;

public class Select_multiple extends AppCompatActivity implements SendRemoveMessage {

    private String TAG = "Multiple";

    public static String RESULT_LIST_OF_IMAGES = "LIST_OF_IMAGES";

    ArrayList<Pojo_ImageGroup> ImageGroup = new ArrayList<>();

    RecyclerView rv_bucketlist;

    DisplayMetrics matrix = new DisplayMetrics();

    private BottomSheetBehavior sheetBehavior;
    private LinearLayout bottom_sheet;

    LinearLayout ll_collapsed;
    RecyclerView rv_data;

    public static ArrayList<String> Selected_Images = new ArrayList<>();

    public static Intent CreateSelection(Context c){
        Intent i = new Intent(c,Select_multiple.class);
        return i;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_multiple);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }


        getWindowManager().getDefaultDisplay().getMetrics(matrix);

        rv_bucketlist = findViewById(R.id.rv_bucketlist);

        SetSheet();


        String[] per = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};

        Permissions.check(Select_multiple.this, per, null, null, new PermissionHandler() {
            @Override
            public void onGranted() {
                
                GetList();

                rv_bucketlist.setAdapter(new Rv_BucketList(ImageGroup,matrix));
                rv_bucketlist.setLayoutManager(new LinearLayoutManager(Select_multiple.this));
            }
        });
    }

    private void SetSheet() {

        ll_collapsed = findViewById(R.id.ll_collapse);
        rv_data = findViewById(R.id.rv_selectedimg);

//        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//
//        int w = matrix.heightPixels/10;
//        rv_data.setPadding(0,0,0,w);
//        rv_data.setClipToPadding(false);

        bottom_sheet = findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet);

        sheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        setLinearlayout();
        SetRecyclerview();

        bottom_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if(newState == BottomSheetBehavior.STATE_COLLAPSED){
                    rv_data.setVisibility(View.GONE);
                    ll_collapsed.setVisibility(View.VISIBLE);

                    Log.d(TAG, "onStateChanged: statechanged");

                    setLinearlayout();


                } else if(newState == BottomSheetBehavior.STATE_EXPANDED){
                    if(Selected_Images.size() == 0){
                        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                    else{
//                        ll_collapsed.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                        iv_icon.setColorFilter(getResources().getColor(R.color.white));
//                        tv_count.setTextColor(getResources().getColor(R.color.white));
//                        bt_clear.setTextColor(getResources().getColor(R.color.white));



                    }

                }else{
                    rv_data.setVisibility(View.VISIBLE);

//                    sheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.d(TAG, "onSlide: statesliced:"+Selected_Images.size());

                if(Selected_Images.size() == 0){
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }


            }
        });

    }

    private void SetRecyclerview() {
        rv_data.setAdapter(new Rv_selectedImage(Selected_Images,matrix,this));
        rv_data.setLayoutManager(new LinearLayoutManager(Select_multiple.this));
    }

    TextView tv_count;
    Button bt_clear;
    ImageView iv_icon;

    private void setLinearlayout() {

        tv_count = findViewById(R.id.tv_counts);
        bt_clear = findViewById(R.id.bt_clear);
        iv_icon = findViewById(R.id.iv_icon);


        tv_count.setText(String.valueOf(Selected_Images.size()));

        if(Selected_Images.size() == 0){
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Selected_Images.clear();
                tv_count.setText(String.valueOf(Selected_Images.size()));
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                Log.d(TAG, "onClick:size "+Selected_Images.size());

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        setLinearlayout();
        SetSheet();
    }

    private void GetList() {

        ArrayList<String> img_path = new ArrayList<>();
        ArrayList<String> bucket_name = new ArrayList<>();

        String[] projection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.DATA
        };

// content:// style URI for the "primary" external storage volume
        Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

// Make the query.
        Cursor cur = managedQuery(images,
                projection, // Which columns to return
                null,       // Which rows to return (all rows)
                null,       // Selection arguments (none)
                null        // Ordering
        );

        Log.i("ListingImages", " query count=" + cur.getCount());

        if (cur.moveToFirst()) {
            String bucket;
            String date;
            String path;
            int bucketColumn = cur.getColumnIndex(
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

            int dateColumn = cur.getColumnIndex(
                    MediaStore.Images.Media.DATE_TAKEN);

            int uric = cur.getColumnIndex(MediaStore.Images.Media.DATA);

            do {
                // Get the field values
                bucket = cur.getString(bucketColumn);
                date = cur.getString(dateColumn);
                path = cur.getString(uric);

                if(!bucket_name.contains(bucket)){
                    bucket_name.add(bucket);
                }

                img_path.add(path);


                // Do something with the values.

//                if(path.contains(bucket)){
//                    Log.d("contain", "GetList: "+bucket);
//                    Log.e("contain", "GetList: "+path);
//
//                }


//                Log.i("ListingImages", " bucket=" + bucket
//                        + "  date_taken=" + date);
//
//                Log.e("path",path);
            } while (cur.moveToNext());

        }

        for(String bucket : bucket_name){
            ArrayList<String> grouped_img_path = new ArrayList<>();

            Pojo_ImageGroup group = new Pojo_ImageGroup();

            for(String img : img_path){

                if(img.contains(bucket)){
                    grouped_img_path.add(img);
                }
            }
            group.setBucket_name(bucket);
            group.setImg_list(grouped_img_path);

            ImageGroup.add(group);
        }

    }

    @Override
    public void Recreate() {
        setLinearlayout();
    }

    public void back(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void done(View view) {
        Intent i = new Intent();
        i.putExtra(RESULT_LIST_OF_IMAGES,Selected_Images);
        setResult(RESULT_OK,i);
        finish();
    }
}

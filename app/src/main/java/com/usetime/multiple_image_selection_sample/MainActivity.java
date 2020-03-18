package com.usetime.multiple_image_selection_sample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.usetime.lib_multiple_image_selection.Select_multiple;

import static com.usetime.lib_multiple_image_selection.Select_multiple.RESULT_LIST_OF_IMAGES;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startActivityForResult(Select_multiple.CreateSelection(MainActivity.this),1280);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1280 && resultCode == RESULT_OK){
            Log.d("arl", "onActivityResult: "+data.getStringArrayListExtra(RESULT_LIST_OF_IMAGES));
        }
    }
}

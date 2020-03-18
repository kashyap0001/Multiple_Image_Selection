package com.usetime.lib_multiple_image_selection.Adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.usetime.lib_multiple_image_selection.R;
import com.usetime.lib_multiple_image_selection.Select_multiple;
import com.usetime.lib_multiple_image_selection.Selection_Area;
import com.usetime.lib_multiple_image_selection.SendRemoveMessage;

import java.util.ArrayList;

public class Rv_SelectionImages extends RecyclerView.Adapter<Rv_SelectionImages.Vholder> {
    ArrayList<String> arl_img;
    DisplayMetrics matrix;
    Context c;
    SendRemoveMessage msg;
    public Rv_SelectionImages(ArrayList<String> arl_img, DisplayMetrics matrix, SendRemoveMessage msg){
        this.arl_img = arl_img;
        this.matrix = matrix;
        this.msg = msg;
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        c = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_img,parent,false);
        return new Vholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Vholder holder, int position) {
        holder.SetData(position);

    }

    @Override
    public int getItemCount() {
        return arl_img.size();
    }

    class Vholder extends RecyclerView.ViewHolder{

        ImageView iv_imgpreview,iv_check,iv_uncheck;

        public Vholder(@NonNull View itemView) {
            super(itemView);

            iv_imgpreview = itemView.findViewById(R.id.iv_preview);
            iv_check = itemView.findViewById(R.id.iv_check);
            iv_uncheck = itemView.findViewById(R.id.iv_uncheck);

            int w = matrix.widthPixels/4;
            iv_imgpreview.setLayoutParams(new LinearLayout.LayoutParams(w,w));
        }

        public void SetData(final int position) {
            Glide.with(c).load(arl_img.get(position)).into(iv_imgpreview);
            String TAG = "ABCXYZ";

            if(Selection_Area.selected.contains(arl_img.get(position))){
                Log.d("ABCXYZ", "SetData: "+position);
                Log.d("ABCXYZ", "SetData: ");
                iv_check.setVisibility(View.VISIBLE);
                iv_uncheck.setVisibility(View.GONE);
            }else{
                iv_check.setVisibility(View.GONE);
                iv_uncheck.setVisibility(View.VISIBLE);
            }

            if(Select_multiple.Selected_Images.contains(arl_img.get(position))){
                Log.d(TAG, "SetData: "+Select_multiple.Selected_Images.size());
                iv_check.setVisibility(View.VISIBLE);
                iv_uncheck.setVisibility(View.GONE);
            }else{
                iv_check.setVisibility(View.GONE);
                iv_uncheck.setVisibility(View.VISIBLE);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = position;
                    if(Select_multiple.Selected_Images.contains(arl_img.get(pos))){
                        Toast.makeText(c, "Previous Selection can not Remove from here", Toast.LENGTH_LONG).show();
                    }
                    else if(Selection_Area.selected.contains(arl_img.get(pos))){
                        Selection_Area.selected.remove(Selection_Area.selected.indexOf(arl_img.get(pos)));
                        iv_check.setVisibility(View.GONE);
                        iv_uncheck.setVisibility(View.VISIBLE);
                        msg.Recreate();
                    }else if(!Selection_Area.selected.contains(arl_img.get(pos))){
                        Selection_Area.selected.add(arl_img.get(pos));
                        iv_uncheck.setVisibility(View.GONE);
                        iv_check.setVisibility(View.VISIBLE);
                        msg.Recreate();
                    }
                }
            });
        }
    }
}

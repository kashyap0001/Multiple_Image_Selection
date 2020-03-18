package com.usetime.lib_multiple_image_selection.Adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.usetime.lib_multiple_image_selection.R;
import com.usetime.lib_multiple_image_selection.Select_multiple;
import com.usetime.lib_multiple_image_selection.SendRemoveMessage;

import java.io.File;
import java.util.ArrayList;

public class Rv_selectedImage extends RecyclerView.Adapter<Rv_selectedImage.Vholder> {

    ArrayList<String> arl_path;
    Context c;
    DisplayMetrics matrix;
    SendRemoveMessage removemsg;

    public Rv_selectedImage( ArrayList<String> arl_path,DisplayMetrics matrix, SendRemoveMessage removemsg){
        this.arl_path = arl_path;
        this.matrix = matrix;
        this.removemsg = removemsg;
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        c = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_selected_image,parent,false);
        return new Vholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Vholder holder, int position) {
        holder.setData(position);

    }

    @Override
    public int getItemCount() {
        return arl_path.size();
    }

    class Vholder extends RecyclerView.ViewHolder{

        ImageView iv, iv_remove;
        TextView tv_name;

        public Vholder(@NonNull View itemView) {
            super(itemView);

            iv = itemView.findViewById(R.id.iv);

            int w = matrix.widthPixels/5;

            iv.setLayoutParams(new LinearLayout.LayoutParams(w,w));

            iv_remove = itemView.findViewById(R.id.iv_remove);
            tv_name = itemView.findViewById(R.id.tv);
        }

        public void setData(final int position) {

            Glide.with(c).load(arl_path.get(position)).into(iv);

            File f = new File(arl_path.get(position));
            String name  =  f.getName();

            tv_name.setText(name);


            iv_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Select_multiple.Selected_Images.remove(arl_path.get(position));
//                    arl_path.remove(position);
                    removemsg.Recreate();

                    notifyDataSetChanged();

                }
            });



        }
    }
}

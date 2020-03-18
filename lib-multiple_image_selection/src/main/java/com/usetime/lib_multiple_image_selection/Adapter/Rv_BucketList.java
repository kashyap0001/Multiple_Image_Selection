package com.usetime.lib_multiple_image_selection.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.usetime.lib_multiple_image_selection.Pojo_ImageGroup;
import com.usetime.lib_multiple_image_selection.R;
import com.usetime.lib_multiple_image_selection.Selection_Area;

import java.util.ArrayList;

public class Rv_BucketList extends RecyclerView.Adapter<Rv_BucketList.Vholder> {

    ArrayList<Pojo_ImageGroup> arl_bucket;
    DisplayMetrics matrix;
    Context c;


    public Rv_BucketList(ArrayList<Pojo_ImageGroup> arl_bucket, DisplayMetrics matrix){
        this.arl_bucket = arl_bucket;
        this.matrix = matrix;
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        c = parent.getContext();

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_bucket,parent,false);

        return new Vholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Vholder holder, int position) {
        holder.setData(position);

    }

    @Override
    public int getItemCount() {
        return arl_bucket.size();
    }

    class Vholder extends RecyclerView.ViewHolder{

        TextView tv_bucketname,tv_count;
        ImageView iv_preview;

        public Vholder(@NonNull View itemView) {
            super(itemView);

            int w = matrix.widthPixels/6;

            iv_preview = itemView.findViewById(R.id.iv_preview);
            iv_preview.setLayoutParams(new LinearLayout.LayoutParams(w,w));
            tv_bucketname = itemView.findViewById(R.id.tv_bucketname);
            tv_count = itemView.findViewById(R.id.tv_count);
        }

        public void setData(final int position) {

            Glide.with(c).load(arl_bucket.get(position).getImg_list().get(0)).into(iv_preview);
            tv_bucketname.setText(arl_bucket.get(position).getBucket_name());
            tv_count.setText(String.valueOf(arl_bucket.get(position).getImg_list().size()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i= new Intent(c, Selection_Area.class);
                    i.putExtra("img_list",arl_bucket.get(position).getImg_list());
                    c.startActivity(i);

                }
            });


        }
    }
}

package com.usetime.lib_multiple_image_selection;

import java.util.ArrayList;

public class Pojo_ImageGroup {
    String Bucket_name;
    ArrayList<String > img_list = new ArrayList<>();

    public String getBucket_name() {
        return Bucket_name;
    }

    public void setBucket_name(String bucket_name) {
        Bucket_name = bucket_name;
    }

    public ArrayList<String> getImg_list() {
        return img_list;
    }

    public void setImg_list(ArrayList<String> img_list) {
        this.img_list = img_list;
    }
}

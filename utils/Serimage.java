package com.uploaddemo.utils;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 刘文杰 on 2018/4/2.
 */

public class Serimage implements Serializable {
    private static final long serialVersionUID = 7382351359868556980L;

    public ArrayList<String> imageArrayList = new ArrayList<>();
    public Serimage(ArrayList<String> imageArrayList)
    {
        this.imageArrayList = imageArrayList;
    }

}

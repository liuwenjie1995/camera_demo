package com.uploaddemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

/**
 * Created by liuwenjie on 2018/3/26.
 *  图片收集使用了封装好的api,计划尝试使用camera2拍摄图片
 *  缓存文件使用使用长度为9的ArrayList进行保存,传递给upload方法
 *
 *
 */

public class FaceAddActivity extends BaseActivity implements View.OnClickListener {

    private Button cameras_button,uploads_button;
    private ArrayList<Bitmap> bitmaps = new ArrayList<>();
    public static final int MEDIA_TYPE_IMAGE = 1;
    private Uri fileUri;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_face);

        cameras_button = (Button)findViewById(R.id.camera_button);
        uploads_button = (Button) findViewById(R.id.upload_api_button);

        cameras_button.setOnClickListener(this);
        uploads_button.setOnClickListener(this);
}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera_button:
                showCamera();
            case R.id.upload_api_button:
                upload_user();

        }
    }

    private void showCamera()
    {
            Intent it = new Intent(FaceAddActivity.this,CameraActivity.class);
            startActivity(it);
    }

    public void upload_user()
    {
            //todo upload user
    }

    public void showallpic()
    {
        //todo 需要将所有图片都显示出来
        try {

            ArrayList<Image> imagelist  = (ArrayList<Image>) this.getIntent().getSerializableExtra("imagelist");
            if(imagelist.size()>1)
            {
                for (Image im : imagelist)
                {

                    Log.d("info","图片高度:"+im.getHeight());

                }
            }
            else
            {
                Log.d("info","没有传来图片");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     *introduce:创建一个文件用于保存数字媒体文件
     *    usage:使用type设置创建文件的格式,1代表图像文件,2代表视频文件
     *   finally:返回一个file,转交给uri函数传递文件地址
     * */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }
}

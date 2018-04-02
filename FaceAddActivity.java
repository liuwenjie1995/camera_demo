package com.uploaddemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    private ImageView imageViews[];
    public static final int MEDIA_TYPE_IMAGE = 1;
    private Uri fileUri;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    public ArrayList<String> filelist= new ArrayList<>();
    private String fileurl;
    private String username;
    private TextView textView;
    @Override
    /**
     * 创建界面的过程，在调用过程中若出现了
     * 图像地址则循环将图片的地址转为bitmap
     * 再循环将image view显示出来
     *
     * */
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_face);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.addlayout);
        cameras_button = (Button)findViewById(R.id.camera_button);
        uploads_button = (Button) findViewById(R.id.upload_api_button);
        uploads_button.setEnabled(false);
        cameras_button.setOnClickListener(this);
        uploads_button.setOnClickListener(this);

        if(filelist.size()!=0)
        {
            imageViews = new ImageView[20];
            showallpic(imageViews);
            for (ImageView view : imageViews)
            {
                layout.addView(view);
            }
        }
        else
        {
            imageViews = new ImageView[1];
            imageViews[0] = (ImageView)findViewById(R.id.locimage);
            imageViews[0].setImageDrawable(getResources().getDrawable(R.drawable.add));
        }



}
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera_button:
                showCamera();
            case R.id.upload_api_button:
            {
                textView = (TextView)findViewById(R.id.editText);
                username = textView.getText().toString().trim();
                upload_user(uploadimage(filelist),username);
            }
        }
    }

    private String uploadimage(ArrayList<String> filelist)
    {
        //todo 使用
        return null;
    }
    private void showCamera()
    {
            Intent it = new Intent(FaceAddActivity.this,CameraActivity.class);
            startActivity(it);
    }
    /**
     * todo upload user
     * 需要上传文件内容
     * 需要将头像内容上传到网站上，返回一个url地址
     * 需要使用到http请求
     *
     **/
    public void upload_user(String filename,String username)
    {

        String url = "cirl -h \"Content-Type:application/json\"-X POST --data '{\"combiner_id\":\"6009_\",\"image_url\":\""
                              +filename+"\", \"extra\": {\"match_op\": \"add\", \"name\": \""
                              +username+"\"}}' imgserver.yunshitu.cn/v1/dispatcher";

    }

    public void showallpic(ImageView[] imageViews)
    {
        //todo 需要将所有图片都显示出来
        try {

            ArrayList<String> imagelist  = (ArrayList<String>) this.getIntent().getSerializableExtra("imagelist");
            if(imagelist.size()>1)
            {
                int flag=0;
                for (String im : imagelist)
                {
                    Log.d("info","use image time");
                    String filename = im;
                    Bitmap bitmap = BitmapFactory.decodeFile(filename);
                    imageViews[flag].setImageBitmap(bitmap);
                    flag++;
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

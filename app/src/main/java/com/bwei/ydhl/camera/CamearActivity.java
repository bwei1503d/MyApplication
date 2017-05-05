package com.bwei.ydhl.camera;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bwei.ydhl.R;
import com.bwei.ydhl.utils.DeviceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CamearActivity extends Activity {

    private Button button_id;
    private Button button_photo;
    private ImageView image;

    public static final int IMAGE = 1 ;
    public static final int CAMERA = 2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camear);


        button_id = (Button) findViewById(R.id.camera_id);
        button_photo = (Button) findViewById(R.id.camera_photo);
        image = (ImageView) findViewById(R.id.image_content);

        button_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showCamera();
            }
        });

        button_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhoto();
            }
        });

    }

    // 缓存的目录
    public static final String SDCARD_ROOT_PATH = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();//路径
    // 缓存的文件名称
    public static final String IMAGE_CAPTURE_NAME = "cameraTmp.jpg"; //照片名称


    // 调用相机 调用这个方法， 图片自动保存到 指定的位置
    public void showCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 存储卡可用 将照片存储在 sdcard
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(SDCARD_ROOT_PATH,IMAGE_CAPTURE_NAME)));
        startActivityForResult(intent, 2);
    }

    public void showPhoto(){
//        Intent intent = new Intent(Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(intent, IMAGE);


        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType("image/*");
        startActivityForResult(getAlbum, 1);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE  && data != null){

            Uri uri = data.getData() ;


            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(uri, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            //图片的真实路径
            String imagePath = c.getString(columnIndex);
            if(c != null){
                c.close();
            }

            System.out.println("uri = " + uri);
            System.out.println("imagePath = " + imagePath);



        } else if (requestCode == CAMERA ) {



            // 获取 拍照图片
            File file = new File(SDCARD_ROOT_PATH+"/"+IMAGE_CAPTURE_NAME);
            System.out.println("bitmapnormal = file " + file.length());

            BitmapFactory.Options options = new BitmapFactory.Options();

            // 设置 true ， 图片没有加载到内存， 只能获取图片的宽高信息
            options.inJustDecodeBounds = true;


            Bitmap bitmapnormal = BitmapFactory.decodeFile(file.toString(),options);
            System.out.println("bitmapnormal = " + bitmapnormal + " options.outWidth" + options.outWidth +   " options.outHeight" + options.outHeight);

            //获取图片的宽度
            int outWidth = options.outWidth;
            //获取图片的高度
            int outHeight = options.outHeight ;

            Point point =  DeviceUtils.getDeviceDisplay(this);
            System.out.println("bitmapnormal = point " + point.x + "   " + point.y);


            int scale = 1 ;
            int scaleX = outWidth / point.x ;
            int scaleY = outHeight / point.y ;

            scale = scaleX >= scaleY ? scaleX : scaleY ;





            //图片加载进内存
            options.inJustDecodeBounds = false ;

            // 缩放图片 改变了图片的宽度高度
            options.inSampleSize = scale ;


//            第二次：将options的值设为Config.RGB_565，会比默认的Config.ARGB_8888减少一半内存；
            options.inPreferredConfig= Bitmap.Config.RGB_565;
            Bitmap bitmap =  BitmapFactory.decodeFile(file.toString(),options);
            System.out.println("bitmap = " + bitmap.getWidth() + "  " + bitmap.getHeight());

            image.setImageBitmap(bitmap);



            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            int optionsize = 80;



            // 压缩的方法 bitmap 压缩到 ByteArrayOutputStream 里面
            bitmap.compress(Bitmap.CompressFormat.JPEG, optionsize, baos);

            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();

                System.out.println("bitmapnormal = file size" + file.length());


                Bitmap bitmap1 =  compressBitmapToWidth(1000,BitmapFactory.decodeFile(file.toString()));


                System.out.println("bitmapnormal bitmap1 " + bitmap1.getWidth() + "  " + bitmap1.getHeight() );


            } catch (IOException e) {
                e.printStackTrace();
            }finally {



            }


        }


    }


    /**
     * 压缩图片到 指定的宽度
     * @param width 压缩后图片的宽度
     */
    private Bitmap compressBitmapToWidth(int width,Bitmap bitmap){
        //获取bitmap 的宽度和高度
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        //创建一个 矩阵
        Matrix matrix = new Matrix();

        float scaleWidth = ((float) width) / bitmapWidth;
//        float scaleHeight = ((float) height) / bitmapHeight;

        matrix.postScale(scaleWidth,scaleWidth);

        Bitmap bitmap1 =  Bitmap.createBitmap(bitmap,0,0,bitmapWidth,bitmapHeight,matrix,true);
        return  bitmap1 ;
    }









}

package com.bwei.ydhl.sensor;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.bwei.ydhl.R;

import java.util.List;

public class SensorActivity extends Activity implements SensorEventListener {


//    x水平朝右为正
//    y水平向前为正
//    z垂直向上为正

    SensorManager sensorManager;
    Sensor mAccelerometer;
    float [] gravity = new float[3] ;

    ImageView imageView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);


        imageView = (ImageView) findViewById(R.id.sensor_imageview);

        getAllSensor();

        //加速度传感器
//        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);

        //重力传感器
//        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),SensorManager.SENSOR_DELAY_FASTEST);

        //方向传感器
//        0°=北，90°=东，180°=南，270°=西。
        // 获取到方向传感器
        Sensor sensor =  sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION) ;
        //注册方向传感器
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_GAME);



    }




    private void getAllSensor(){

        //获得传感器 管理器
         sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // 获取手机 所支持的内置传感器 list
        List<Sensor> list =  sensorManager.getSensorList(Sensor.TYPE_ALL);


        for (Sensor sensor : list) {
            System.out.println("sensor = " + sensor.getName());
        }

    }


    // 传感器回调
    float linear_acceleration[] = new float[3];
    float predegree = 0 ;
    /**
     * 传感器数据变化时回调
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {

            case Sensor.TYPE_ACCELEROMETER:

                // 官方滤波器 去掉重力加速度
                final float alpha = 0.8f;
//
                gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
                gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
                gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];
////
                linear_acceleration[0] = event.values[0] - gravity[0];
                linear_acceleration[1] = event.values[1] - gravity[1];
                linear_acceleration[2] = event.values[2] - gravity[2];
//                System.out.println("accelerometer = " + linear_acceleration[0] + " " + linear_acceleration[1] +  "  " + linear_acceleration[2] );

                System.out.println("accelerometer = " + event.values[0] + " " + event.values[1] +  "  " + event.values[2] );
                break;
            case Sensor.TYPE_GRAVITY:
                gravity[0] = event.values[0] ;
                gravity[1] = event.values[1] ;
                gravity[2] = event.values[2] ;

//                System.out.println("TYPE_GRAVITY = " + gravity[0] + "  " + gravity[1] + " " + gravity[2]);

                break;
            case Sensor.TYPE_ORIENTATION:
                //方向传感器 数据变化 回调

                float degree = event.values[0];// 存放了方向值
//                System.out.println("degree = " + degree);
                /**动画效果*/

//                fromDegrees：旋转的开始角度。
//
//                toDegrees：旋转的结束角度。
//
//                pivotXType：X轴的伸缩模式，可以取值为ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT。
//
//                pivotXValue：X坐标的伸缩值。
//
//                pivotYType：Y轴的伸缩模式，可以取值为ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT。
//
//                pivotYValue：Y坐标的伸缩值。
//                http://blog.csdn.net/qq_15003505/article/details/47284349
// 0.0f是以左上角为起点旋转，0.5f是以中心点为起点旋转，1.0f是以右下角为起点旋转

//                Animation.ABSOLUTE：具体的坐标值，指绝对的屏幕像素单位
//                Animation.RELATIVE_TO_SELF：相对自己的坐标值，0.1f是指自己的坐标值乘以0.1
//                Animation.RELATIVE_TO_PARENT：相对父容器的坐标值，0.1f是指父容器的坐标值乘以0.1
//
//
                RotateAnimation animation = new RotateAnimation(predegree, degree,
                        Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                animation.setDuration(200);
//                如果fillAfter的值为true,则动画执行后，控件将停留在执行结果的状态
                animation.setFillAfter(true);
                imageView.startAnimation(animation);
                predegree = -degree;


                break;

        }


    }

    /**
     * 传感器精度变化时回调
     * @param sensor
     * @param accuracy
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//        SensorManager.SENSOR_STATUS_ACCURACY_LOW

        System.out.println("sensor = " + sensor);
        System.out.println("sensor = accuracy" + accuracy);


    }

    @Override
    protected void onPause() {
        super.onPause();
        if(sensorManager != null){
            sensorManager.unregisterListener(this);
        }

    }
}

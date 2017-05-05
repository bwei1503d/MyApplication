package com.bwei.ydhl;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.xutils.x;

/**
 * Created by muhanxi on 17/4/13.
 */

public class IApplication extends Application {

    public int share ;

    @Override
    public void onCreate() {
        super.onCreate();
//        http://blog.csdn.net/vipzjyno1/article/details/23206387

//        http://www.cnblogs.com/tianzhijiexian/p/4034304.html




        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);
        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);

//        share = 6 ;


        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.

    }
}

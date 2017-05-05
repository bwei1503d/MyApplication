package com.bwei.ydhl.upload;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bwei.ydhl.R;

public class MainActivity extends Activity {
    private File file;
    private static final String TAG="MainActivity";
    private TextView textView;
    private Button button; 
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        textView = (TextView)findViewById(R.id.textView);
        button = (Button)findViewById(R.id.upload);
        file = new File(Environment.getExternalStorageDirectory(), "01.png");
        
        button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				uploadFile(file);

			}
		});
    }



    public void uploadFile(File imageFile) {
        Log.i(TAG, "upload start");
        try {
            String requestUrl = "http://qhb.2dyt.com/UploadService/upload/execute.do" ;

            Map<String, String> params = new HashMap<String, String>();
            params.put("username", "111");
            params.put("pwd", "123456");
            params.put("age", "23");
            FormFile formfile = new FormFile(imageFile.getName(), imageFile, "image", "application/octet-stream");
            FileUtil.post(requestUrl, params, formfile);
            Log.i(TAG, "upload success");
        } catch (Exception e) {
            Log.i(TAG, "upload error");
            e.printStackTrace();
        }
        Log.i(TAG, "upload end");
    }
}
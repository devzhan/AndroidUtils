package com.android.zone.leak;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Debug;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.zone.R;

import java.io.File;
import java.io.IOException;
import java.lang.ref.SoftReference;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class MemoryLeakActivity extends AppCompatActivity implements CallBack {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_memory_leak);
        ImageView imageView = findViewById(R.id.iv_memoryleak);
        for (int i =0;i<10;i++){
            CallBackManager.addCallBack(this);
        }
//        test();
//        try {
//            Debug.dumpHprofData("/mnt/sdcard/dump.hprof");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        File downloadsDirectory = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS);
        File directory = new File(downloadsDirectory, "Utils-");
        boolean success = directory.mkdirs();
        if (!success && !directory.exists()) {
            throw new UnsupportedOperationException(
                    "Could not create leak directory " + directory.getPath());
        }
       File file =  new File(directory, "dumpHrof");
        try {
            Debug.dumpHprofData(file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CallBackManager.removeCallBack(this);
    }

    @Override
    public void doWork() {
        // do sth
    }

    public void test() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}

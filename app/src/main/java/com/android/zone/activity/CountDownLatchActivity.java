package com.android.zone.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.zone.R;
import com.android.zone.concurrency.CountDownLatchProject;
import com.android.zone.concurrency.CountDownLatchdevalper;

public class CountDownLatchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        CountDownLatchProject project = new CountDownLatchProject(10);
        new Thread(project).start();
        for (int i = 0; i < 10; i++) {
            CountDownLatchdevalper count = new CountDownLatchdevalper(project);
            new Thread(count).start();
        }
    }
}

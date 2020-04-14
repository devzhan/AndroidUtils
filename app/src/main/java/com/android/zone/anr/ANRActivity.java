package com.android.zone.anr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.zone.R;

public class ANRActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anr);
        waitTest();
    }
    public void waitTest() {
        String s = "";
            for (; ; ) {
            }

    }
}

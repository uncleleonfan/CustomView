package com.example.slidelock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private LockView mLockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLockView = (LockView) findViewById(R.id.lock_view);
        mLockView.setUnlockListener(new LockView.OnUnlockListener() {
            @Override
            public void onUnlock() {
                finish();
            }
        });

    }
}

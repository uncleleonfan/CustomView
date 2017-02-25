package com.example.crosslayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private CrossLayout mCrossLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCrossLayout = (CrossLayout) findViewById(R.id.cross_layout);
    }

    public void onRevert(View view) {
        mCrossLayout.revert();
    }
}

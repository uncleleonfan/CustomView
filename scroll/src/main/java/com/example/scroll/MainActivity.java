package com.example.scroll;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.tv);
    }

    public void startScrollTo(View view) {
        mTextView.scrollTo(-1 * 50, 0);
    }

    public void startScrollBy(View view) {
        mTextView.scrollBy(-1 * 20, 0);
    }
}

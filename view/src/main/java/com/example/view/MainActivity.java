package com.example.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        //不使用xml，通过代码同样可以实现activity_main.xml的布局，说明布局文件不是必须的，只是一种开发工具而已
        RelativeLayout relativeLayout = new RelativeLayout(this);
        int padding = getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin);
        relativeLayout.setPadding(padding, padding, padding, padding);

        TextView textView = new TextView(this);
        textView.setText("Hello World!");
        relativeLayout.addView(textView);

        setContentView(relativeLayout);
    }
}

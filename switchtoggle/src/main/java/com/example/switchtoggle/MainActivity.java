package com.example.switchtoggle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SwitchToggleView mSwitchToggleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwitchToggleView = (SwitchToggleView) findViewById(R.id.switch_toggle);
        mSwitchToggleView.setOnSwitchListener(new SwitchToggleView.OnSwitchListener() {
            @Override
            public void onSwitchChange(boolean isClose) {
                String info = isClose ? "Close" : "Open";
                Toast.makeText(MainActivity.this, info, Toast.LENGTH_SHORT).show();
            }
        });

    }
}

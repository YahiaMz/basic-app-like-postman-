package com.aplication.uplaodimage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class responce extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responce);
        init();
    }

    void init() {
        this.textView = findViewById(R.id.responce);

        String data;
        if (getIntent().getExtras() != null) {
            data = getIntent().getExtras().getString("data");
        } else  data = "No Data";
        this.textView.setText(data);
    }

}
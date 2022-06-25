package com.aplication.uplaodimage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText f1,f2,f3,f4,f5,f6,f7,f8;
    EditText v1,v2,v3,v4,v5,v6,v8;
    EditText uri;
    ToggleButton toggleBtn;
    Button chooseIMgbtn;
    Bitmap bitmap = null;
    Button submit ;
    int PICK_IMAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        this.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    System.out.println(getFV());
                    sendRequest();

                }catch ( Exception e) {
                    System.out.println(e.getMessage());
                }

            }
        });
    this.chooseIMgbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
           startActivityForResult(intent , PICK_IMAGE);
        }
    });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //System.out.println("IMage selected " + uri.toString());

        if( resultCode== Activity.RESULT_OK) {
            Uri uri = data.getData();
            try {
                 this.bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String BitMapToString(Bitmap bitmap){
        if (bitmap ==null )
            return  "";
        ByteArrayOutputStream baos= new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    void init() {
      this.submit = findViewById(R.id.submit);
       this.chooseIMgbtn= findViewById(R.id.chosephoto);
        this.uri = findViewById(R.id.Uri);
        this.toggleBtn = findViewById(R.id.togglebtn);
        this.f1 = findViewById(R.id.feild_name1);
        this.f2 = findViewById(R.id.feild_name2);
        this.f3 = findViewById(R.id.feild_name3);
        this.f4=  findViewById(R.id.feild_name4);
        this.f5 = findViewById(R.id.feild_name5);
        this.f6 = findViewById(R.id.feild_name6);
        this.f7 = findViewById(R.id.feild_name7);

        this.f8 = findViewById(R.id.feild_name8);

        this.v1 = findViewById(R.id.feild_value1);
        this.v2 = findViewById(R.id.feild_value2);
        this.v3 = findViewById(R.id.feild_value3);
        this.v4 = findViewById(R.id.feild_value4);
        this.v5 = findViewById(R.id.feild_value5);
        this.v6 = findViewById(R.id.feild_value6);
        this.v8 = findViewById(R.id.feild_value8);


    };

    void sendRequest ( ) {
        StringRequest request = new StringRequest(
                (toggleBtn.isChecked()) ? Request.Method.POST : Request.Method.GET, uri.getText().toString() , new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(getApplicationContext() , responce.class);
                intent.putExtra("data" , response);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Intent intent = new Intent(getApplicationContext() , responce.class);
                intent.putExtra("data" , "fucking err");
                startActivity(intent);
            }
        }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return getFV();
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
   Map <String ,String > getFV ( ) {
        Map<String , String> parms = new HashMap<>();
         if (!f1.getText().toString().isEmpty() ) {
             parms.put(f1.getText().toString() , v1.getText().toString() );
         }
       if (!f2.getText().toString().isEmpty()) {
           parms.put(f2.getText().toString() , v2.getText().toString() );
       }
       if (!f3.getText().toString().isEmpty()) {
           parms.put(f3.getText().toString() , v3.getText().toString() );
       }
       if (!f4.getText().toString().isEmpty()) {
           parms.put(f4.getText().toString() , v4.getText().toString() );
       }
       if (!f5.getText().toString().isEmpty()) {
           parms.put(f5.getText().toString() , v5.getText().toString() );
       }
       if (!f6.getText().toString().isEmpty()) {
           parms.put(f6.getText().toString() , v6.getText().toString() );
       }
       if (!f8.getText().toString().isEmpty()) {
           parms.put(f8.getText().toString() , v8.getText().toString() );
       }
       if(!f7.getText().toString().isEmpty()) {
           parms.put(f7.getText().toString() , BitMapToString(this.bitmap));
       }

        return  parms;
   }
}
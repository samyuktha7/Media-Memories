package com.example.samyuktha.mediamemories;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.net.URLConnection;


public class Main3Activity extends AppCompatActivity {

    Firstfrag f1;
    SecondFragment f2;
    android.support.v4.app.FragmentTransaction ft;
    android.support.v4.app.FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

       fm = getSupportFragmentManager();

        Intent sk= getIntent();

        String sk1= sk.getStringExtra("hellos");

        f1=new Firstfrag();
         f2=new SecondFragment();
        ft= fm.beginTransaction();


        File f= new File(sk1);
        String mimeType = URLConnection.guessContentTypeFromName(sk1);

        if(mimeType.startsWith("image")) {

            ft.add(R.id.mylays, f1, "first");
            ft.commit();
          f1.getpaths(sk1);


        }
        else
        {
            ft.add(R.id.mylays, f2, "second");
            ft.commit();
            f2.getpath(sk1);
        }


//

    }
}

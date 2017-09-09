package com.example.samyuktha.mediamemories;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class Firstfrag extends Fragment {

    Bitmap bt;
    String getso;

     public void getpaths(String rec)
    {
        this.getso=rec;
    }



    ImageView ims;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View v=inflater.inflate(R.layout.fragment_firstfrag, container, false);
        ims=(ImageView)v.findViewById(R.id.img);
        Bitmap bk= BitmapFactory.decodeFile(getso);
        ims.setImageBitmap(bk);
        return v ;
    }

}

package com.example.samyuktha.mediamemories;


import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;


import com.github.rtoshiro.view.video.FullscreenVideoLayout;

import java.io.File;
import java.io.IOException;



public class SecondFragment extends Fragment  {

    FullscreenVideoLayout videoLayout;
        VideoView vms;
    String panda;


   public void getpath(String rec1)
    {
        this.panda=rec1;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v1=inflater.inflate(R.layout.fragment_second, container, false);
      Uri uri = Uri.fromFile(new File(panda));

        videoLayout = (FullscreenVideoLayout)v1.findViewById(R.id.videoview);
        videoLayout.setActivity(getActivity());


        try {
            videoLayout.setVideoURI(uri);

        } catch (IOException e) {
            e.printStackTrace();
        }



        return v1 ;
    }




}

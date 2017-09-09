package com.example.samyuktha.mediamemories;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by samyuktha on 9/7/2017.
 */

public class Myrecycleview extends RecyclerView.Adapter<Myrecycleview.Myadapter> {




    List<String> getslist;


    Myrecycleview(List<String> gets)
    {
        this.getslist=gets;

    }


    @Override
    public Myrecycleview.Myadapter onCreateViewHolder(ViewGroup parent, int viewType) {


        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_each, parent, false);
        return new Myadapter(v,getslist);
    }

    @Override
    public void onBindViewHolder(Myrecycleview.Myadapter holder, int position) {


       Bitmap myBitmap = BitmapFactory.decodeFile(getslist.get(position));

        if(myBitmap != null) {

            holder.photo1.setImageBitmap(myBitmap);
        }
        else
        {


            Bitmap bMap = ThumbnailUtils.createVideoThumbnail(getslist.get(position), MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
            holder.photo1.setImageBitmap(bMap);
        }

    }

    @Override
    public int getItemCount() {

        return getslist.size();
    }

    public static class Myadapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView photo1;
        CardView cds;
        List<String> getslist1;
        public Myadapter(View itemView, List<String> gets1) {

            super(itemView);
            this.getslist1=gets1;
                    photo1 = (ImageView) itemView.findViewById(R.id.img);
            itemView.setOnClickListener(this);

//
        }

        @Override
        public void onClick(View v) {
            Intent ins=new Intent(v.getContext(),Main3Activity.class);
            int m= getAdapterPosition();
            ins.putExtra("hellos",getslist1.get(m));
            v.getContext().startActivity(ins);
        }
    }
}

package com.example.samyuktha.mediamemories;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    ListView lv2;
    ArrayAdapter<String> adap2;
    MainActivity ma;
    Cursor cur1,cur2;
    List<String> fileslist1;
    RecyclerView rec1;
    ContentResolver cr;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager1;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM");
    SimpleDateFormat formatter1 = new SimpleDateFormat("YYYY");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        rec1=(RecyclerView)findViewById(R.id.recycs);

        gaggeredGridLayoutManager1 = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rec1.setLayoutManager(gaggeredGridLayoutManager1);


        cr =getContentResolver();

        cur1 = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null , null, null);
        cur2=cr.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null , null, null);


        long m= System.currentTimeMillis();


        String dateString1 = formatter.format(new Date(m));
        String exact = formatter1.format(new Date(m));


        fileslist1=new ArrayList<String>();
        while(cur1.moveToNext()) {


            Long s=cur1.getLong(cur1.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
            String sa=cur1.getString(cur1.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
            s*=1000;


            String dateString = formatter.format(new Date(s));
        String mk2= formatter1.format(new Date(s));


            if(dateString1.equals(dateString))
            {
                Log.d("ellos",dateString+ " "+dateString1);
                if(!(mk2.equals(exact))) {

                    fileslist1.add(sa);
                }


            }

        }

        while(cur2.moveToNext())
        {
            Long s1=cur2.getLong(cur2.getColumnIndex(MediaStore.Video.Media.DATE_ADDED));
            String sa1=cur2.getString(cur2.getColumnIndex(MediaStore.Video.VideoColumns.DATA));
            s1*=1000;
            String dateString2 = formatter.format(new Date(s1));
            String mk1=formatter1.format(new Date(s1));
            if(dateString1.equals(dateString2))
            {

                Log.d("ellos",dateString2+ " "+dateString1);

                if(!(mk1.equals(exact))) {
                    fileslist1.add(sa1);
                }


            }

        }
int k = fileslist1.size();

        Toast.makeText(Main2Activity.this, " "+k,Toast.LENGTH_SHORT).show();



      Myrecycleview  mines1= new Myrecycleview(fileslist1);
        rec1.setAdapter(mines1);


    }
}

package com.example.samyuktha.mediamemories;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DatePicker.OnDateChangedListener {

  FloatingActionButton fab;

    List<String> fileslist, pathAbs, galleryList;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    Cursor crus;
    Cursor crusvid;
    Myrecycleview mines;
    ArrayAdapter<String> adap;
    DatePicker dpo;
    ContentResolver cr;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    PendingIntent pendingIntent;
    int a1,a2,a3;
    final private int p=20;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alternate_lay);


        dpo = (DatePicker)findViewById(R.id.picker);
        dpo.init(dpo.getYear(), dpo.getMonth(),dpo.getDayOfMonth(), dateSetListener);
        Calendar calendar = Calendar.getInstance();
        calendar.set(dpo.getYear(), dpo.getMonth(), dpo.getDayOfMonth());
        long startTime = calendar.getTimeInMillis();
        String dateString1 = formatter.format(new Date(startTime));




            //alarm service
            AlarmManager alarmman = (AlarmManager) getSystemService(ALARM_SERVICE);
            Calendar c = Calendar.getInstance();

            Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver1.class); // AlarmReceiver1 = broadcast receiver

            pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmIntent.setData((Uri.parse("custom://" + System.currentTimeMillis())));
            alarmman.cancel(pendingIntent);

            Calendar alarmStartTime = Calendar.getInstance();
            Calendar now = Calendar.getInstance();
            alarmStartTime.set(Calendar.HOUR_OF_DAY, 16);
            alarmStartTime.set(Calendar.MINUTE, 28);
            alarmStartTime.set(Calendar.SECOND, 0);
            if (now.after(alarmStartTime)) {
                Log.d("Hey", "Added a day");
                alarmStartTime.add(Calendar.DATE, 1);
            }

            //alarmman.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(),AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);

            alarmman.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            Log.d("Alarm", "Alarms set for everyday 8 am.");







    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case p: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    execute(a1,a2,a3);


                } else {
                    Toast.makeText(MainActivity.this, "permission is denied", Toast.LENGTH_SHORT).show();

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

        }
    }



    private DatePicker.OnDateChangedListener dateSetListener = new DatePicker.OnDateChangedListener() {

        public void onDateChanged(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

            a1=year;
            a2=monthOfYear;
            a3=dayOfMonth;
            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            {

                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},20);

            }
            else

            {

                execute(a1,a2,a3);

            }
        }

    };


    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Toast.makeText(MainActivity.this,"date chnaged",Toast.LENGTH_SHORT).show();
    }


    public void execute(int a , int b, int c)
    {
        cr = getContentResolver();
        crus = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        crusvid = cr.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, null);


        Calendar calendar = Calendar.getInstance();
        calendar.set(a,b, c);
        String dateString1 = formatter.format(new Date(calendar.getTimeInMillis()));


        fileslist = new ArrayList<String>();
        pathAbs = new ArrayList<String>();
        crus.moveToFirst();
        crusvid.moveToFirst();
        while (crus.moveToNext()) {

            Long s = crus.getLong(crus.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
            String sa = crus.getString(crus.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
            String sl = crus.getString(crus.getColumnIndex(MediaStore.Images.ImageColumns.DATA));


            s *= 1000;
            String dateString = formatter.format(new Date(s));

            if (dateString1.equals(dateString)) {


                pathAbs.add(sl);

            }

        }


        while (crusvid.moveToNext()) {
            Long s1 = crusvid.getLong(crusvid.getColumnIndex(MediaStore.Video.Media.DATE_ADDED));
            String sa1 = crusvid.getString(crusvid.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
            String sl1 = crusvid.getString(crusvid.getColumnIndex(MediaStore.Video.VideoColumns.DATA));

            s1 *= 1000;
            String dateString2 = formatter.format(new Date(s1));
            if (dateString1.equals(dateString2)) {

                fileslist.add(sl1);
                pathAbs.add(sl1);


            }

        }

        recyclerView = (RecyclerView)findViewById(R.id.rec);
        recyclerView.setHasFixedSize(true);

        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);
        Log.d("hello"," "+pathAbs);


        mines= new Myrecycleview(pathAbs);
        recyclerView.setAdapter(mines);

    }


public void fabaction(View v )
{
    Toast.makeText(MainActivity.this, "Gallery", Toast.LENGTH_SHORT).show();
    cr=getContentResolver();
    crus = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
    crusvid = cr.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
    galleryList=new ArrayList<String>();

    while (crus.moveToNext()) {

        String sl = crus.getString(crus.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
            galleryList.add(sl);

    }


    while (crusvid.moveToNext()) {

        String sl1 = crusvid.getString(crusvid.getColumnIndex(MediaStore.Video.VideoColumns.DATA));
            galleryList.add(sl1);


    }


    recyclerView = (RecyclerView)findViewById(R.id.rec);
    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));

    mines= new Myrecycleview(galleryList);
    recyclerView.setAdapter(mines);


}

}




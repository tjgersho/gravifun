package com.ParadigmMotion.GraviFun;



import android.app.Activity;
//import android.media.MediaPlayer;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;


public class MainActivity extends Activity   {
    private static final String TAG = MainActivity.class.getSimpleName();
     private ContentView contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         requestWindowFeature(Window.FEATURE_NO_TITLE);

         getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
         getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);



        Log.d(TAG, "View Added");


        contentView = new ContentView(this);
        setContentView(contentView);
    }

    @Override
    protected  void onStart()   {
        super.onStart();

        Log.d(TAG, "ONSTART");


    }


    @Override
    protected  void onResume(){
        super.onResume();

        Log.d(TAG, "ON RESUME ");
        setContentView(contentView);
        contentView.thread.setRunning(true);
    }

    @Override
    protected  void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "Destroying ...");



    }

    @Override
    protected void onStop(){
        super.onStop();


        Log.d(TAG, "Stopping ... ");



    }

     @Override
    protected  void onPause(){
         super.onPause();

         Log.d(TAG, "Pausing ... ");
         contentView.thread.setRunning(false);
     }



}

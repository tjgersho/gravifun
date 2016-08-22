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

   public MediaPlayer bgMusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         requestWindowFeature(Window.FEATURE_NO_TITLE);

         getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
         getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);



        Log.d(TAG, "View Added");

       bgMusic = MediaPlayer.create(getApplicationContext(), R.raw.lightyears);
       bgMusic.setLooping(true);bgMusic.start();

        setContentView(new ContentView(this));
    }
    @Override
    protected  void onResume(){
        super.onResume();
        bgMusic = MediaPlayer.create(getApplicationContext(), R.raw.lightyears);
       bgMusic.setLooping(true);
       bgMusic.start();

    }

    @Override
    protected  void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "Destroying ...");


        if(bgMusic != null) {
           if(bgMusic.isPlaying()){
                bgMusic.stop();
           }
           bgMusic.release();
           bgMusic = null;
        }


    }

    @Override
    protected void onStop(){
        super.onStop();
        if(bgMusic != null) {
          if(bgMusic.isPlaying()){
             bgMusic.stop();
          }
            bgMusic.release();
            bgMusic = null;

        }
        Log.d(TAG, "Stopping ... ");

    }



}

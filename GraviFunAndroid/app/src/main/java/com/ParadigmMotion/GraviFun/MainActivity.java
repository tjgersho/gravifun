package com.ParadigmMotion.GraviFun;



import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity{
    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         requestWindowFeature(Window.FEATURE_NO_TITLE);

         getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(new ContentView(this));

        Log.d(TAG, "View Added");


    }

    @Override
    protected  void onDestroy(){
        Log.d(TAG, "Destroying ...");
        super.onDestroy();;

    }

    @Override
    protected void onStop(){

        Log.d(TAG, "Stopping ... ");
        super.onStop();
    }



}

package com.ParadigmMotion.GraviFun;


import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * Created by tjgersho on 8/12/16.
 */
public class ContentView extends SurfaceView implements SurfaceHolder.Callback {



    private static final String TAG = ContentView.class.getSimpleName();

    private MainThread thread;
    GravController gravController;
    Globals g= Globals.getInstance();

    public ContentView(Context context){

       super(context);
        g.setContext(context);

        getHolder().addCallback(this);



        setFocusable(true);
        Log.d("TJG", "ContentView Constructor");

    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
        getHolder().addCallback(this);
       // thread = new MainThread(getHolder(), this);

        g.setWindowHeight(height);
        g.setWindowWidth(width);
       // Log.d("TJG", "Global Get Width Rotate View" + Integer.toString(g.getWindowWidth()));
      //  Log.d("TJG", "Global Get Height Rotate View" + Integer.toString(g.getWindowHeight()));

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){


     //   Log.d("TJG", "Global Get Width Content View Surface Created" + Integer.toString(g.getWindowWidth()));
     //   Log.d("TJG", "Global Get Height Content View Surface Created" + Integer.toString(g.getWindowHeight()));
        gravController = GravController.getInstance(getHolder());

        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();

    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder){

        if(thread != null) {
            thread.setRunning(false);

            while(thread != null) {
                try {
                    thread.join();
                    thread = null;
                } catch (InterruptedException e) {
                }
            }
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
      gravController.onTouchEvent(event);
        return true;
    }



    @Override
    protected void onDraw(Canvas canvas){
        Log.d("TJG", "On Draw: ");

    }



}

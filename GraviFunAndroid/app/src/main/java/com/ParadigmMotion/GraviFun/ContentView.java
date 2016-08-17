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
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        setFocusable(true);
        Log.d("TJG", "ContentView Constructor");

    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);

        g.setWindowHeight(height);
        g.setWindowWidth(width);
        Log.d("TJG", "Global Get Width Rotate View" + Integer.toString(g.getWindowWidth()));
        Log.d("TJG", "Global Get Height Rotate View" + Integer.toString(g.getWindowHeight()));

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){


        g.setWindowHeight(getHeight());
        g.setWindowWidth(getWidth());

        Log.d("TJG", "Global Get Width Content View Surface Created" + Integer.toString(g.getWindowWidth()));
        Log.d("TJG", "Global Get Height Content View Surface Created" + Integer.toString(g.getWindowHeight()));
        gravController = GravController.getInstance();

        thread.setRunning(true);
        thread.start();

    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry){
            try{
                thread.join();
                retry = false;

            }catch (InterruptedException e){
                //try again shutting down the thread
            }
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        Log.d("TJG", "MOtion Event");
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(event.getY() > getHeight() - 50){

                thread.setRunning(false);
                ((Activity)getContext()).finish();

            }else{
                Log.d(TAG, "Coords: x=" + event.getX() + ",y= "+ event.getY());
                 gravController.addMass(event.getX(), event.getY());
            }


        }


        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas){
        Log.d("TJG", "On Draw: ");

    }


    protected void update(){
        //Log.d("TJGU", "On Update: ");

       gravController.update();

    }


    protected void render(Canvas canvas){
       // Log.d("TJGR", "On Render: ");
        gravController.drawSpace(canvas);

    }
}

package com.ParadigmMotion.GraviFun;


import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;


/**
 * Created by tjgersho on 8/12/16.
 */
public class ContentView extends SurfaceView implements SurfaceHolder.Callback {



    private static final String TAG = ContentView.class.getSimpleName();

    public MainThread thread;
    GravController gravController;
    Globals g = Globals.getInstance();
    public MediaPlayer bgMusic;

    public ContentView(Context context){

       super(context);
        g.setContext(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        setFocusable(true);
        bgMusic = MediaPlayer.create(context, R.raw.lightyears);
        bgMusic.setLooping(true);bgMusic.start();


        //   Log.d("TJG", "Global Get Width Content View Surface Created" + Integer.toString(g.getWindowWidth()));
        //   Log.d("TJG", "Global Get Height Content View Surface Created" + Integer.toString(g.getWindowHeight()));

    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
        getHolder().addCallback(this);
       // thread = new MainThread(getHolder(), this);

        g.setWindowHeight(height);
        g.setWindowWidth(width);
       // Log.d("TJG", "Global Get Width Rotate View" + Integer.toString(g.getWindowWidth()));
      //  Log.d("TJG", "Global Get Height Rotate View" + Integer.toString(g.getWindowHeight()));
        thread.setRunning(true);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){



        g.setWindowHeight(getHeight());
        g.setWindowWidth(getWidth());
        g.setIszeromass(true);

        gravController = GravController.getInstance();

     //   Log.d("TJG", "Global Get Width Content View Surface Created" + Integer.toString(g.getWindowWidth()));
     //   Log.d("TJG", "Global Get Height Content View Surface Created" + Integer.toString(g.getWindowHeight()));


        gravController.runballs(30);
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);

            thread.start();


    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        thread.setRunning(false);
        boolean retry = true;
        while(retry){
            try{
                thread.join();
                retry = false;

            }catch (InterruptedException e){
                //try again shutting down the thread
            }
        }

        if(bgMusic != null) {
            if(bgMusic.isPlaying()){
                bgMusic.stop();
            }
            bgMusic.release();
            bgMusic = null;

        }
        gravController.clearMasses();

        Log.d("TJG", "SUrface Destroyed");
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
       // Log.d("TJG", "MOtion Event"+ Integer.toString(event.getAction()));
        if(event.getAction() == MotionEvent.ACTION_DOWN){
             int btn;
            btn = whichButton(event);
               Log.d("TJG", "Event Case: "+ Integer.toString(btn));
                switch (btn){
                    case 0:
                        Log.d(TAG, "Coords: x=" + event.getX() + ",y= "+ event.getY());
                        g.setShoudClear(false);
                        g.addMass(event.getX(), event.getY());
                      //  g.addEarthMovement(event.getX(), event.getY());
                        break;
                    case 1:
                        g.setShoudClear(true);
                        g.setShouldRunBalls(false);
                        g.runballs(0);
                        g.setIssingularity(false);
                        g.setIszeromass(true);
                        g.setIsdarkenergy(false);
                        break;
                    case 2:
                        g.setShoudClear(true);
                        g.setShouldRunBalls(true);
                        g.runballs(10);
                        g.setIssingularity(false);
                        g.setIszeromass(true);
                        g.setIsdarkenergy(false);

                        break;
                    case 3:
                        g.setShoudClear(true);
                        g.setShouldRunBalls(true);
                        g.runballs(50);
                        g.setIssingularity(false);
                        g.setIszeromass(true);
                        g.setIsdarkenergy(false);

                        break;
                    case 4:
                        g.setShoudClear(true);
                        g.setShouldRunBalls(true);
                        g.runballs(100);
                        g.setIssingularity(false);
                        g.setIszeromass(true);
                        g.setIsdarkenergy(false);

                        break;
                    case 5:
                        g.setIssingularity(false);
                        g.setIszeromass(false);
                        g.setIsdarkenergy(true);
                        break;
                    case 6:
                        g.setIssingularity(false);
                        g.setIszeromass(true);
                        g.setIsdarkenergy(false);
                        break;
                    case 7:
                        g.setIssingularity(true);
                        g.setIszeromass(false);
                        g.setIsdarkenergy(false);
                        break;
                }





        }

         return true;
        //return super.onTouchEvent(event);
    }

    private int whichButton(MotionEvent event){

        float clickY = event.getY();
        float clickX = event.getX();

        if(clickY>(float)0.05*getHeight() && clickY < (float)(getHeight()-0.05*getHeight())){
            return 0;
        }else{

            if(clickY < (float)0.05*getHeight()){  /// Is Grav-#

                if(clickX < getWidth()/4){
                    return 1;
                }else if(clickX > getWidth()/4 && clickX < getWidth()/2){
                    return 2;
                }else if(clickX > getWidth()/2 && clickX < (float)3/4*getWidth()){
                    return 3;
                }else{
                    return 4;
                }


            }else{  // is Pointer Mass Setting

                if(clickX < getWidth()/3) {
                    return 5;
                }else if(clickX > getWidth()/3 && clickX < (float) 2/3*getWidth()) {
                    return 6;
                }else{
                    return 7;
                }

            }

        }


    }

    @Override
    protected void onDraw(Canvas canvas){
        Log.d("TJG", "On Draw: ");

    }

    public void update(){


        gravController.update();

    }

    public void render(Canvas canvas){
      gravController.drawSpace(canvas);
    }




}

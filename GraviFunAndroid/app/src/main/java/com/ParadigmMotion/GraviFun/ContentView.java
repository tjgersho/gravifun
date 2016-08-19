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
       // Log.d("TJG", "Global Get Width Rotate View" + Integer.toString(g.getWindowWidth()));
      //  Log.d("TJG", "Global Get Height Rotate View" + Integer.toString(g.getWindowHeight()));

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){


        g.setWindowHeight(getHeight());
        g.setWindowWidth(getWidth());
        g.setIszeromass(true);

     //   Log.d("TJG", "Global Get Width Content View Surface Created" + Integer.toString(g.getWindowWidth()));
     //   Log.d("TJG", "Global Get Height Content View Surface Created" + Integer.toString(g.getWindowHeight()));
        gravController = GravController.getInstance(getHolder());

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
             int btn;
            if((btn = whichButton(event)) != 0){
               Log.d("TJG", "Event Case: "+ Integer.toString(btn));
                switch (btn){
                    case 1:
                        gravController.clearMasses();
                        g.setIssingularity(false);
                        g.setIszeromass(true);
                        g.setIsdarkenergy(false);
                        break;
                    case 2:
                        gravController.clearMasses();
                        g.setIssingularity(false);
                        g.setIszeromass(true);
                        g.setIsdarkenergy(false);
                        gravController.runballs(10);
                        break;
                    case 3:
                        gravController.clearMasses();
                        g.setIssingularity(false);
                        g.setIszeromass(true);
                        g.setIsdarkenergy(false);
                        gravController.runballs(50);
                        break;
                    case 4:
                        gravController.clearMasses();
                        g.setIssingularity(false);
                        g.setIszeromass(true);
                        g.setIsdarkenergy(false);
                        gravController.runballs(100);
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


            }else{
                Log.d(TAG, "Coords: x=" + event.getX() + ",y= "+ event.getY());
                 gravController.addMass(event.getX(), event.getY());
            }


        }


        return super.onTouchEvent(event);
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


    protected void update(){
        //Log.d("TJGU", "On Update: ");

       gravController.update();

    }


    protected void render(Canvas canvas){
       // Log.d("TJGR", "On Render: ");
        gravController.drawSpace(canvas);

    }
}

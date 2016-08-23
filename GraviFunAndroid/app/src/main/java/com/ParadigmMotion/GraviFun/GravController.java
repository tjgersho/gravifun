package com.ParadigmMotion.GraviFun;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
//import android.media.MediaPlayer;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tjgersho on 8/16/16.
 */



public class GravController  {
    private static GravController instance = null;
    protected GravController(SurfaceHolder surfaceHolder){
         this.surfaceHolder = surfaceHolder;

        masses = new ArrayList<>();
        gravityPhysics = new GravityPhysics();
    }

    private SurfaceHolder surfaceHolder;
    public static GravController getInstance(SurfaceHolder surfaceHolder){
        if(instance == null){
            Log.d("TJG", "GraView Constructor");
            instance = new GravController(surfaceHolder);
        }
        return instance;
    }

    Globals g = Globals.getInstance();


    private MediaPlayer spawnPlayer = MediaPlayer.create(g.getAppContext(), R.raw.bottlepop);

    private int windowWidth = g.getWindowWidth();
    private int windowHeight = g.getWindowHeight();

    public enum GravState {
        ISZERO, DARKENERGY, SINGULARITY
    }

    public GravState gravstate = GravState.ISZERO;


    private double currentTime = System.currentTimeMillis();

    private double now;
    private static PointerGrav pnter = new PointerGrav(0,0);
    private  ArrayList<Mass> masses;

    private GravityPhysics gravityPhysics;


    private Paint singularityBtnPaint = new Paint();
    private Paint darkenergyBtnPaint = new Paint();
    private Paint nomassBtnPaint = new Paint();
    private Paint grav0BtnPaint = new Paint();
    private Paint grav10BtnPaint = new Paint();
    private Paint grav50BtnPaint = new Paint();
    private Paint grav100BtnPaint = new Paint();
    private Paint txtPaint = new Paint();

    private double earthX;
    private double earthY;

  public  void update(){
      windowWidth = g.getWindowWidth();
      windowHeight= g.getWindowHeight();

      if(g.shouldclearMasses()){
          this.clearMasses();
      }

      if(g.getMassestoadd().size()>0){
          for (GravPoint pt : g.getMassestoadd()){
              Log.d("TJG", "Adding pts " + Double.toString(pt.x) + " y: "+ Double.toString(pt.y));
              this.addMass(pt.x, pt.y);
          }
      }
      if(g.getShouldRunBalls()){
          this.runballs(g.getBallstorun());
      }
      pnter.updatePos(earthX, earthY);
      now = System.currentTimeMillis();

      double delta_t = (now - currentTime) / 1000;

      currentTime = now;
      masses = gravityPhysics.update(masses, pnter, delta_t, windowWidth, windowHeight, gravstate);


    //Log.d("TJG", "Number of masses " + Integer.toString(masses.size()));


      g.clearAddmasses();
      g.setShouldRunBalls(false);
      g.setShoudClear(false);


  }


    public  void  addMass(double x, double y){
              masses.add(new Mass(x, y));
             spawnPlayer.start();
     }
    public void clearMasses(){
        masses.clear();
    }

    public int runballs(int qty){
      //  Log.d("TJG", "Run Balls qty: " + Integer.toString(qty) );
        if(masses.size()<qty){
            gravityPhysics.spawnMass(masses, windowWidth, windowHeight);
            return runballs(qty);
        }else{

            return 1;
        }

    }

  public void drawSpace(){

      Canvas canvas = this.surfaceHolder.lockCanvas();

      if(canvas != null) {
          canvas.drawColor(Color.argb(100, 0, 0, 0));


          for (Mass mass : masses) {
              mass.drawSelf(canvas);
          }

          pnter.drawSelf(canvas);

          drawButtons(canvas);

              surfaceHolder.unlockCanvasAndPost(canvas);

         }
   }

    private void drawBtn(String txt, int txtSize, int colortxt, int colorbtn, float cx,
                         float cy, float rectwidth, float rectheight,
                         Paint pnttxt, Paint  pntbtn, Canvas canvas){

        pntbtn.setColor(colorbtn);
        pntbtn.setStyle(Paint.Style.FILL);


        pnttxt.setTextSize(txtSize);
        pnttxt.setColor(colortxt);

        canvas.drawRect(cx, cy, cx+rectwidth, cy+rectheight, pntbtn);

        float textWidth = pnttxt.measureText(txt);

        canvas.drawText(txt, cx+(rectwidth/2-textWidth/2), cy+(rectheight/2), pnttxt);

    }
    private void drawButtons(Canvas canvas){


        float btnheight = (float)0.05*windowHeight;
        float topbtnwidth = (float)0.25*windowWidth;
        float bottmbtnwidth = (float)windowWidth/3;

        float topbtny = 0;
        float grav0origx = 0;
        float grav10origx = topbtnwidth;
        float grav50origx = 2*topbtnwidth;
        float grav100origx = 3*topbtnwidth;

        float bottmbtny = windowHeight-btnheight;

        float darkorigx = 0;
        float zeroorigx = bottmbtnwidth;
        float singorigx = 2*bottmbtnwidth;

        int textSize = 30;

        drawBtn("Singularity", textSize , Color.WHITE, Color.argb(100,100,100,100), singorigx, bottmbtny, bottmbtnwidth, btnheight, txtPaint, singularityBtnPaint, canvas);
        drawBtn("Dark Energy", textSize , Color.WHITE, Color.argb(100,50,100,100), darkorigx, bottmbtny, bottmbtnwidth, btnheight, txtPaint, darkenergyBtnPaint, canvas);
        drawBtn("Zero Mass", textSize , Color.WHITE, Color.argb(100,50,50,100), zeroorigx, bottmbtny, bottmbtnwidth, btnheight, txtPaint, nomassBtnPaint, canvas);

        drawBtn("Grav-0", textSize , Color.WHITE, Color.argb(100,50,50,100), grav0origx, topbtny, topbtnwidth, btnheight, txtPaint, grav0BtnPaint, canvas);
        drawBtn("Grav-10", textSize , Color.WHITE, Color.argb(100,50,50,50), grav10origx, topbtny, topbtnwidth, btnheight, txtPaint, grav10BtnPaint, canvas);
        drawBtn("Grav-50", textSize , Color.WHITE, Color.argb(100,100,200,100), grav50origx, topbtny, topbtnwidth, btnheight, txtPaint, grav50BtnPaint, canvas);
        drawBtn("Grav-100", textSize , Color.WHITE, Color.argb(100,200,50,100), grav100origx, topbtny, topbtnwidth, btnheight, txtPaint, grav100BtnPaint, canvas);


    }



    public void onTouchEvent(MotionEvent event) {

        Log.d("TJG", "MOtion Event"+ Integer.toString(event.getAction()));
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            int btn;
            if((btn = whichButton(event.getX(), event.getY())) != 0){
                Log.d("TJG", "Event Case: "+ Integer.toString(btn));
                switch (btn){
                    case 1:
                        g.setShoudClear(true);
                        g.setShouldRunBalls(false);
                        g.runballs(0);

                        break;
                    case 2:
                        g.setShoudClear(true);
                        g.setShouldRunBalls(true);
                        g.runballs(10);
                       gravstate = GravController.GravState.ISZERO;



                        break;
                    case 3:
                        g.setShoudClear(true);
                        g.setShouldRunBalls(true);
                        g.runballs(50);
                        gravstate = GravController.GravState.ISZERO;

                        break;
                    case 4:
                        g.setShoudClear(true);
                        g.setShouldRunBalls(true);
                        g.runballs(100);
                        gravstate = GravController.GravState.ISZERO;

                        break;
                    case 5:
                        gravstate = GravController.GravState.DARKENERGY;
                        break;
                    case 6:
                        gravstate = GravController.GravState.ISZERO;
                        break;
                    case 7:
                        gravstate = GravController.GravState.SINGULARITY;
                        break;
                }


            }else{
                Log.d("TJG", "Coords: x=" + event.getX() + ",y= "+ event.getY());
                g.addMass(event.getX(), event.getY());

                earthX = event.getX();
                earthY = event.getY();


            }


        }



    }

    private int whichButton(float clickX, float clickY){


        if(clickY>(float)0.05*windowHeight && clickY < (float)(windowHeight-0.05*windowHeight)){
            return 0;
        }else{

            if(clickY < (float)0.05*windowHeight){  /// Is Grav-#

                if(clickX < windowWidth/4){
                    return 1;
                }else if(clickX > windowWidth/4 && clickX < windowWidth/2){
                    return 2;
                }else if(clickX > windowWidth/2 && clickX < (float)3/4*windowWidth){
                    return 3;
                }else{
                    return 4;
                }


            }else{  // is Pointer Mass Setting

                if(clickX < windowWidth/3) {
                    return 5;
                }else if(clickX > windowWidth/3 && clickX < (float) 2/3*windowWidth) {
                    return 6;
                }else{
                    return 7;
                }

            }

        }


    }


}

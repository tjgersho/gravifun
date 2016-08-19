package com.ParadigmMotion.GraviFun;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
//import android.media.MediaPlayer;
import android.util.Log;
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

        masses = new ArrayList<Mass>();
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
   // private MediaPlayer destroyPlayer = MediaPlayer.create(g.getAppContext(), R.raw.bottlepop);
   // private MediaPlayer spawnPlayer = MediaPlayer.create(g.getAppContext(), R.raw.bottlepop);

    private int windowWidth = g.getWindowWidth();
    private int windowHeight = g.getWindowHeight();

    private double GravC;

    private double currentTime = System.currentTimeMillis();

    private double now;
    private static PointerGrav pnter = new PointerGrav(0,0);
    private volatile List<Mass> masses;


    private Paint singularityBtnPaint = new Paint();
    private Paint darkenergyBtnPaint = new Paint();
    private Paint nomassBtnPaint = new Paint();
    private Paint grav0BtnPaint = new Paint();
    private Paint grav10BtnPaint = new Paint();
    private Paint grav50BtnPaint = new Paint();
    private Paint grav100BtnPaint = new Paint();
    private Paint txtPaint = new Paint();


  public synchronized void update(){
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

      GravC = 500*((double)windowWidth/1920);

      now = System.currentTimeMillis();

      double delta_t = (now - currentTime) / 1000;

      currentTime = now;

      for(Mass mass : masses) {

           mass.posX = mass.posX + delta_t*mass.velX;
           mass.posY = mass.posY + delta_t*mass.velY;

          double relaxation = 1.0;
          if(Math.sqrt(Math.pow(mass.velX,2)+Math.pow(mass.velX,2))*mass.mass() > 30){
              relaxation  = 0.98;
          }

          mass.velX = relaxation* mass.velX + mass.forceX/mass.mass() * delta_t;
          mass.velY = relaxation* mass.velY + mass.forceY/mass.mass() * delta_t;

      }

      List<Absorber> absorbList = new ArrayList<>();

      List<Destroyer> destroyList = new ArrayList<>();

      int me = 0;



    for (Mass mass_me : masses) {

        mass_me.forceX = 0;
        mass_me.forceY = 0;
        int them = 0;
        for (Mass mass_them : masses) {


            if (mass_me != mass_them) {

                double distX = mass_them.posX - mass_me.posX;
                double distY = mass_them.posY - mass_me.posY;

                double dist = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));

                if (dist < (mass_them.radius + mass_me.radius)) {
                    dist = mass_them.radius + mass_me.radius;
                    if (mass_them.radius < mass_me.radius) {
                        Boolean isInabsorblist = false;
                        for (Absorber abs : absorbList) {

                            if (abs.absorbs == me && abs.absorbs == them) {
                                isInabsorblist = true;
                                break;
                            }

                        }

                        if (!isInabsorblist) {
                            absorbList.add(new Absorber(me, them));

                        }

                    }

                }

                double force = GravC * mass_me.mass() * mass_them.mass() / (dist * dist);


                mass_me.forceX = mass_me.forceX + force * (distX / dist);
                mass_me.forceY = mass_me.forceY + force * (distY / dist);

            }
            them++;
        }
         double distXEarth = pnter.x - mass_me.posX;
         double distYEarth = pnter.y - mass_me.posY;
         double distEarth = Math.sqrt(Math.pow(distXEarth,2) + Math.pow(distYEarth,2));

         double forceEarth = GravC*mass_me.mass()*pnter.mass()/(distEarth*distEarth);

        mass_me.forceX = mass_me.forceX + forceEarth*(distXEarth/distEarth);
        mass_me.forceY = mass_me.forceY + forceEarth*(distYEarth/distEarth);
        mass_me.stayOnScreen();

        if(mass_me.radius > windowWidth*0.3){
            destroyList.add(new Destroyer(me));
        }

        me++;
    }

     // Log.d("TJG", "Mass Data " + Double.toString(masses.get(1).forceX));

    //  Log.d("TJG", "PosX " + Double.toString(masses.get(1).posX));
    //  Log.d("TJG", "VelX " + Double.toString(masses.get(1).velX));

   for (Absorber abs : absorbList){
       double prevMass = masses.get(abs.who).mass();
       double velInitialX = masses.get(abs.who).velX;
       double velInitialY = masses.get(abs.who).velY;

       masses.get(abs.who).radius = Math.sqrt(Math.pow(masses.get(abs.who).radius,2) + Math.pow(masses.get(abs.absorbs).radius,2));

       masses.get(abs.who).velX = (masses.get(abs.who).velX*prevMass + masses.get(abs.absorbs).velX*masses.get(abs.absorbs).mass())/(prevMass + masses.get(abs.absorbs).mass());
       masses.get(abs.who).velY = (masses.get(abs.who).velY*prevMass + masses.get(abs.absorbs).velY*masses.get(abs.absorbs).mass())/(prevMass + masses.get(abs.absorbs).mass());

       masses.get(abs.who).forceX = masses.get(abs.who).forceX + 0.1*(masses.get(abs.who).mass()*(masses.get(abs.absorbs).velX-velInitialX))/delta_t;
       masses.get(abs.who).forceY = masses.get(abs.who).forceY + 0.1*(masses.get(abs.who).mass()*(masses.get(abs.absorbs).velY-velInitialY))/delta_t;

       masses.remove(abs.absorbs);
       spawnMass();

   }

      for (Destroyer de : destroyList){
         // Log.d("TJG", "Destry loop de "+ Integer.toString(de.who));
          masses.remove(de.who);
          ///destroyPlayer.start();
      }

    //Log.d("TJG", "Number of masses " + Integer.toString(masses.size()));

      destroyList.clear();
      absorbList.clear();
      g.clearAddmasses();
      g.setShouldRunBalls(false);
      g.setShoudClear(false);

      Log.d("TJG", "Number of points "+ Integer.toString(masses.size()));
  }

  public void spawnMass(){

      double x = Math.random()*windowWidth;
      double y = Math.random()*windowHeight;
      //Log.d("TJG", "SPAWN MASS X: " + Double.toString(x) + "  Width " + Integer.toString(windowWidth));
      masses.add(new Mass(x, y, windowWidth, windowHeight));

  }
  public synchronized void   addMass(double x, double y){


              masses.add(new Mass(x, y, windowWidth, windowHeight));
              //spawnPlayer.start();
              pnter.updatePos((int) x,(int)y);


  }
    public void clearMasses(){
        masses.clear();
    }

    public int runballs(int qty){
      //  Log.d("TJG", "Run Balls qty: " + Integer.toString(qty) );
        if(masses.size()<qty){
            spawnMass();
            return runballs(qty);
        }else{

            return 1;
        }

    }

  public synchronized void drawSpace(Canvas canvas){
      if(canvas != null) {
          canvas.drawColor(Color.argb(100, 0, 0, 0));


          for (Mass mass : masses)
              if (mass.radius > 0.03 * windowWidth && mass.radius <= 0.28 * windowWidth) {

                  float[] stopsGradient = new float[]{0, 0.5f, 1};
                  int[] colorsGradient = new int[]{Color.BLUE, Color.WHITE, mass.color};
                  float xC = (float) mass.posX;
                  float yC = (float) mass.posY;
                  RadialGradient radialGradient = new RadialGradient(xC, yC, (float) ((float) mass.radius * 0.7), colorsGradient, stopsGradient, Shader.TileMode.CLAMP);

                  mass.paint.setDither(true);
                  mass.paint.setAntiAlias(true);

                  mass.paint.setShader(radialGradient);
                  canvas.drawCircle((float) mass.posX, (float) mass.posY, (float) mass.radius, mass.paint);

              } else if (mass.radius > 0.28 * windowWidth) {
                  float[] stopsGradient = new float[]{0, 0.5f, 1};
                  int[] colorsGradient = new int[]{Color.BLACK, Color.WHITE, mass.color};
                  float xC = (float) mass.posX;
                  float yC = (float) mass.posY;
                  RadialGradient radialGradient = new RadialGradient(xC, yC, (float) ((float) mass.radius * 0.8), colorsGradient, stopsGradient, Shader.TileMode.CLAMP);

                  mass.paint.setDither(true);
                  mass.paint.setAntiAlias(true);

                  mass.paint.setShader(radialGradient);
                  canvas.drawCircle((float) mass.posX, (float) mass.posY, (float) mass.radius, mass.paint);
              } else {
                  canvas.drawCircle((int) mass.posX, (int) mass.posY, (int) mass.diameter, mass.paint);
              }
          }
          canvas.drawCircle(pnter.x, pnter.y, (int) pnter.diameter(), pnter.paint);
          drawButtons(canvas);

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



}

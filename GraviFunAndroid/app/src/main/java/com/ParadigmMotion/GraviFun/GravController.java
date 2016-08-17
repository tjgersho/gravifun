package com.ParadigmMotion.GraviFun;


import android.graphics.Canvas;
import android.graphics.Color;
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

    private int windowWidth = g.getWindowWidth();
    private int windowHeight = g.getWindowHeight();

    private double GravC;
    //private long druation = 1000;
    private double currentTime = System.currentTimeMillis();
    //private  long startTime = currentTime;
    //private static long runTime = 0;
    private double now;
    private static PointerGrav pnter = new PointerGrav(0,0);
    private volatile List<Mass> masses = new ArrayList<Mass>();




  public void update(){


      GravC = 1000*((double)windowWidth/1920);

      now = System.currentTimeMillis();

      double delta_t = (now - currentTime) / 1000;

      currentTime = now;

      for(Mass mass : masses) {

           mass.posX = mass.posX + delta_t*mass.velX;
           mass.posY = mass.posY + delta_t*mass.velY;

          double relaxation = 1.0;
          if(Math.sqrt(Math.pow(mass.velX,2)+Math.pow(mass.velX,2))*mass.mass() > 20){
              relaxation  = 0.95;
          }

          mass.velX = relaxation* mass.velX + mass.forceX/mass.mass() * delta_t;
          mass.velY = relaxation* mass.velY + mass.forceY/mass.mass() * delta_t;

          //Log.d("TJG", "Mass Check " + Double.toString(mass.mass()));
      }

      List<Absorber> absorbList = new ArrayList<>();

      int[] destroyArray = new int[masses.size()];
      int me = 0;
      int them = 0;
synchronized (masses) {
    for (Mass mass_me : masses) {

        mass_me.forceX = 0;
        mass_me.forceY = 0;

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


               // Log.d("TJG", "Force " + Double.toString(force));
               // Log.d("TJG", "DistX " + Double.toString(distX));
               // Log.d("TJG", "Dist " + Double.toString(dist));

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

        if(mass_me.radius > windowWidth*0.3){
            //destroyArray[dest]
        }

        me++;
    }
   }
     // Log.d("TJG", "Mass Data " + Double.toString(masses.get(1).forceX));

    //  Log.d("TJG", "PosX " + Double.toString(masses.get(1).posX));
    //  Log.d("TJG", "VelX " + Double.toString(masses.get(1).velX));



  }

  public void spawnMasses(){

      double x = Math.random()*windowWidth;
      double y = Math.random()*windowHeight;
      Log.d("TJG", "SPAWN MASS X: " + Double.toString(x) + "  Width " + Integer.toString(windowWidth));
      masses.add(new Mass(x, y));
  }
  public synchronized void   addMass(float x, float y){
      try {
          synchronized (instance.surfaceHolder) {
              masses.add(new Mass(x, y));

              pnter.updatePos((int) x,(int)y);
          }
      }catch (Exception e){
          Log.d("TJG", "Error Adding mass " + e.getMessage());
      }
  }
    public void clearMasses(){
        masses.clear();
    }

    public int runballs(int qty){
        Log.d("TJG", "Run Balls qty: " + Integer.toString(qty) );
        if(masses.size()<qty){
            spawnMasses();
            return runballs(qty);
        }else{

            return 1;
        }

    }

  public void drawSpace(Canvas canvas){
      if(canvas != null) {
          canvas.drawColor(Color.argb(100, 0, 0, 0));
          for (Mass mass : masses) {
              canvas.drawCircle((int) mass.posX, (int) mass.posY, (int) mass.diameter, mass.paint);

          }

          canvas.drawCircle((int)pnter.x, (int)pnter.y, (int) pnter.diameter(), pnter.paint);

      }
   }

  //public void run(Canvas canvas){
  // drawSpace(canvas);
  //}

}

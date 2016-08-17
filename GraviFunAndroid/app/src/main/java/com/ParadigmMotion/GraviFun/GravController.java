package com.ParadigmMotion.GraviFun;


import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tjgersho on 8/16/16.
 */



public class GravController  {
    private static GravController instance = null;
    protected GravController(){}

    public static GravController getInstance(){
        if(instance == null){
            Log.d("TJG", "GraView Constructor");
            instance = new GravController();
        }
        return instance;
    }

    Globals g = Globals.getInstance();

    private int windowWidth = g.getWindowWidth();;
    private int windowHeight =g.getWindowHeight();

    private static double GravC;
    private long druation = 1000;
    private static long currentTime = System.currentTimeMillis();
    private static long startTime = currentTime;
    private static long runTime = 0;
    private long now;
    private static PointerGrav pnter = new PointerGrav(0,0);
    private List<Mass> masses = new ArrayList<Mass>();




  public void update(){

      GravC = 0.0005*(windowWidth/1920);
      now = System.currentTimeMillis();
      long delta_t = now - currentTime;
      currentTime = now;

      for(Mass mass : masses) {

           mass.posX = mass.posX + delta_t*mass.velX;
           mass.posY = mass.posY + delta_t*mass.velY;

          double relaxation = 1.0;
          //if()

      }
  }

  public void spawnMasses(){

      double x = Math.random()*windowWidth;
      double y = Math.random()*windowHeight;
      Log.d("TJG", "SPAWN MASS X: " + Double.toString(x) + "  Width " + Integer.toString(windowWidth));
      masses.add(new Mass(x, y));
  }
  public void  addMass(float x, float y){

    masses.add(new Mass(x, y));

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
      }
   }

  //public void run(Canvas canvas){
  // drawSpace(canvas);
  //}

}

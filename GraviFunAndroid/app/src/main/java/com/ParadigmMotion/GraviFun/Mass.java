package com.ParadigmMotion.GraviFun;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.Log;

/**
 * Created by tjgersho on 8/12/16.
 */

public class Mass {

    public double posX;
    public double posY;
    public double velX;
    public double velY;
    public double forceX;
    public double forceY;

    public double radius;
    public double diameter;



    private int windowWidth;
    private int windowHeight;
    public int color;
    public Paint paint;

    private Globals g;


   public Mass(double x, double y){
       g = Globals.getInstance();
       windowWidth = g.getWindowWidth();
       windowHeight = g.getWindowHeight();
       posX = x;
       posY = y;

       radius = Math.floor(Math.random()*Math.min(windowWidth, windowHeight)* 0.005)+ Math.min(windowWidth, windowHeight)*0.002;
       diameter  = 2*this.radius;

       int r = (int)Math.floor(Math.random()*255);
       int g = (int)Math.floor(Math.random()*255);
       int b = (int)Math.floor(Math.random()*255);
      // Log.d("TJG", "Red "+ Integer.toString(r) + ", Green "+ Integer.toString(g) + ", Blue "+ Integer.toString(b));

       color = Color.argb(255, r, g, b);

       paint = new Paint();
       paint.setColor(color);


   }

    public double mass(){
      // Log.d("TJG", "MASS Rad" + Double.toString(this.radius));
         return 0.5*this.radius*this.radius*Math.PI*(1920/Math.min(windowWidth,windowHeight));

    }

public void drawSelf(Canvas canvas){

    if (this.radius > 0.03 * windowWidth && this.radius <= 0.28 * windowWidth) {

        float[] stopsGradient = new float[]{0, 0.5f, 1};
        int[] colorsGradient = new int[]{Color.BLUE, Color.WHITE, this.color};
        float xC = (float) this.posX;
        float yC = (float) this.posY;
        RadialGradient radialGradient = new RadialGradient(xC, yC, (float) ((float) this.radius * 0.7), colorsGradient, stopsGradient, Shader.TileMode.CLAMP);

        this.paint.setDither(true);
        this.paint.setAntiAlias(true);

        this.paint.setShader(radialGradient);
        canvas.drawCircle((float) this.posX, (float) this.posY, (float) this.radius, this.paint);

    } else if (this.radius > 0.28 * windowWidth) {
        float[] stopsGradient = new float[]{0, 0.5f, 1};
        int[] colorsGradient = new int[]{Color.BLACK, Color.WHITE, this.color};
        float xC = (float) this.posX;
        float yC = (float) this.posY;
        RadialGradient radialGradient = new RadialGradient(xC, yC, (float) ((float) this.radius * 0.8), colorsGradient, stopsGradient, Shader.TileMode.CLAMP);

        this.paint.setDither(true);
        this.paint.setAntiAlias(true);

        this.paint.setShader(radialGradient);
        canvas.drawCircle((float) this.posX, (float) this.posY, (float) this.radius, this.paint);
    } else {
        canvas.drawCircle((int) this.posX, (int) this.posY, (int) this.diameter, this.paint);
    }

}

    public void stayOnScreen(){

        if(this.posX+this.radius>windowWidth){
            this.posX = windowWidth - this.radius;
            this.velX = -this.velX;
            this.forceX = -this.forceX;
        }
        if(this.posX-this.radius< 0)
        {
            this.posX = this.radius;
            this.velX = -this.velX;
            this.forceX = -this.forceX;
        }
        if (this.posY+this.radius > windowHeight){
            this.posY = windowHeight - this.radius;
            this.velY = -this.velY;
            this.forceY = -this.forceY;

        }
        if(this.posY-this.radius<0){
            this.posY = this.radius;
            this.velY = -this.velY;
            this.forceY = -this.forceY;
        }

    }



}

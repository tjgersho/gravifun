package com.ParadigmMotion.GraviFun;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by tjgersho on 8/16/16.
 */
public class PointerGrav {
    private static PointerGrav instance = null;
    public static PointerGrav getInstance(int x, int y){
        if(instance == null){
            instance = new PointerGrav(x, y);
        }
        return instance;
    }

    public double x =-1000;
    public double y = -1000;
    private int color;
    public Paint paint;
    private Globals g = Globals.getInstance();



    protected  PointerGrav(int x, int y){
        this.x = x;
        this.y = y;

        color = Color.argb(255, 255, 255, 255);

        paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
    }

    public double diameter(){

       return  20;
    }
    public void drawSelf(Canvas canvas){
        canvas.drawCircle((float)this.x, (float)this.y, (int) this.diameter(), this.paint);
    }
    public double mass(GravController.GravState state){


        if(state == GravController.GravState.ISZERO) {
            return 0.0001;
        }

        if(state == GravController.GravState.SINGULARITY){
            return 20000;
        }

        if(state == GravController.GravState.DARKENERGY){
            return -20000;
        }else{

            return 0.0001;
        }

    }

    public void updatePos(double x, double y){
        this.x = x;
        this.y = y;

    }

}

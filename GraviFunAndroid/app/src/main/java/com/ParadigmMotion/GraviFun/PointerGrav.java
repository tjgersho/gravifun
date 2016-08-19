package com.ParadigmMotion.GraviFun;


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

    public int x =-1000;
    public int y = -1000;
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

    public double mass(){


        if(g.getIszeromass()) {
            return 0;
        }

        if(g.getIssingularity()){
            return 20000;
        }

        if(g.getiddarkenergy()){
            return -20000;
        }else{

            return 0;
        }

    }

    public void updatePos(int x, int y){
        this.x = x;
        this.y = y;


    }

}

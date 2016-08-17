package com.ParadigmMotion.GraviFun;


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

    public int x;
    public int y;



    protected  PointerGrav(int x, int y){
        this.x = x;
        this.y = y;

    }

    public void updatePos(int x, int y){
        this.x = x;
        this.y = y;


    }

}

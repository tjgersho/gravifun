package com.ParadigmMotion.GraviFun;

/**
 * Created by tjgersho on 8/16/16.
 */
public class Globals {

    private static Globals instance;

    private int windowWidth;
    private int windowHeight;
    private Globals(){}


    public int getWindowWidth(){
        return this.windowWidth;
    }
    public int getWindowHeight(){
        return this.windowHeight;
    }

    public void setWindowWidth(int w){
        this.windowWidth = w;
    }



    public void setWindowHeight(int h){
        this.windowHeight = h;
    }

    public static synchronized Globals getInstance(){

        if(instance==null){
            instance = new Globals();
        }
        return instance;
    }
}

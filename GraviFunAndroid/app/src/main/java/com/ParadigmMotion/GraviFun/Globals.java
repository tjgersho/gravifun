package com.ParadigmMotion.GraviFun;

/**
 * Created by tjgersho on 8/16/16.
 */
public class Globals {

    private static Globals instance;

    private int windowWidth;
    private int windowHeight;
    private Globals(){}
    private boolean iszeromass;
    private boolean isdarkenergy;
    private boolean issingularity;

    public boolean getIszeromass(){
        return this.iszeromass;
    }
    public boolean getiddarkenergy(){
        return this.isdarkenergy;
    }

    public boolean getIssingularity(){
        return this.issingularity;
    }

    public void setIszeromass(boolean b){
        this.iszeromass = b;
    }
    public void setIsdarkenergy(boolean b){
        this.isdarkenergy = b;
    }
    public void setIssingularity(boolean b){
        this.issingularity = b;
    }

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

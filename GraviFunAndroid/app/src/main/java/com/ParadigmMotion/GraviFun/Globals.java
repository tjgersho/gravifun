package com.ParadigmMotion.GraviFun;


import android.content.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by tjgersho on 8/16/16.
 */
public class Globals {

    private static Globals instance;

    private int windowWidth;
    private int windowHeight;
    private Globals(){

    }
    public static Globals getInstance(){

        if(instance==null){
            instance = new Globals();
        }
        return instance;
    }

    private boolean iszeromass = true;
    private boolean isdarkenergy = false;
    private boolean issingularity = false;

    public boolean getIszeromass(){
        return this.iszeromass;
    }

    public boolean getIsdarkenergy(){
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




    private ArrayList<GravPoint> addmassarray = new ArrayList<>();

    public void addMass(double x, double y){
        GravPoint mass = new GravPoint(x,y);
        addmassarray.add(mass);

    }

    public ArrayList<GravPoint> getMassestoadd(){
        return this.addmassarray;
    }

    public void clearAddmasses(){
        this.addmassarray.clear();
    }

    private boolean shoudClear = false;

    public void setShoudClear(boolean b){
        this.shoudClear = b;
    }




    public boolean shouldclearMasses(){
            return this.shoudClear;
    }

    private boolean shouldRunBalls = false;

    public void setShouldRunBalls(boolean b){
        this.shouldRunBalls = b;
    }
    public boolean getShouldRunBalls(){
        return this.shouldRunBalls;
    }

    private int ballstorun;
    public void runballs(int n){
        this.ballstorun = n;
    }
    public int getBallstorun(){return this.ballstorun;}


    private  Context context;
    public void setContext (Context con){
        this.context = con;
    }
    public Context getAppContext(){
        return this.context;
    }

    public synchronized void updateDone(){

        this.clearAddmasses();
        this.setShouldRunBalls(false);
        this.setShoudClear(false);
    }
}

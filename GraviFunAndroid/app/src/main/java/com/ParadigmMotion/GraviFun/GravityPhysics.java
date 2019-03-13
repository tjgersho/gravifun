package com.paradigmmotion.gravifun;

import android.media.MediaPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tjgersho on 8/23/16.
 */
public class GravityPhysics {
    private double GravC;
    private Globals g = Globals.getInstance();
    private MediaPlayer destroyPlayer = MediaPlayer.create(g.getAppContext(), R.raw.explosion);
    public ArrayList<Mass> update(ArrayList<Mass> masses, PointerGrav pnter, double delta_t, int windowWidth, int windowHeight, GravController.GravState gravstate){

        GravC = 500*((double)windowWidth/1920);



        for(Mass mass : masses) {

            mass.posX = mass.posX + delta_t*mass.velX;
            mass.posY = mass.posY + delta_t*mass.velY;

            double relaxation = 1.0;
            if(Math.sqrt(Math.pow(mass.velX,2)+Math.pow(mass.velX,2))*mass.mass() > 20){
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
            double forceEarth;
            if(distEarth>1) {
                forceEarth = GravC * mass_me.mass() * pnter.mass(gravstate) / (distEarth * distEarth);
            }else{
                distEarth = 1;
                forceEarth = GravC * mass_me.mass() * pnter.mass(gravstate) / (distEarth * distEarth);
            }
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
            spawnMass(masses, windowWidth, windowHeight);

        }

        for (Destroyer de : destroyList){
            // Log.d("TJG", "Destry loop de "+ Integer.toString(de.who));
            masses.remove(de.who);
            ///destroyPlayer.start();
            destroyPlayer.start();
        }
        destroyList.clear();
        absorbList.clear();

        return masses;

    }
    public void spawnMass(ArrayList<Mass> masses, int windowWidth, int windowHeight){

        double x = Math.random()*windowWidth;
        double y = Math.random()*windowHeight;
        //Log.d("TJG", "SPAWN MASS X: " + Double.toString(x) + "  Width " + Integer.toString(windowWidth));
        masses.add(new Mass(x, y));

    }

}

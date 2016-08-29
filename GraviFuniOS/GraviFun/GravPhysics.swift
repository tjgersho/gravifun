//
//  GravPhysics.swift
//  GraviFun
//
//  Created by Travis Gershon on 8/29/16.
//  Copyright © 2016 Travis Gershon. All rights reserved.
//

import Foundation


class GravPhysics {
   
    var instance = GravPhysics()
    var g = Game.instance
    
    
    
    public func run (delta_t: Double){
   
    
        let GravC: Double = 500*(Double(g.windowWidth/1920))
        
        
        
        for mass in g.masses {
            
            mass.posX = mass.posX + delta_t*mass.velX;
            mass.posY = mass.posY + delta_t*mass.velY;
            
            var relaxation:Double = 1.0;
            if(sqrt(pow(mass.velX,2)+pow(mass.velX,2))*mass.mass() > 20){
                relaxation  = 0.98;
            }
            
            mass.velX = relaxation*mass.velX + mass.forceX/mass.mass() * delta_t;
            mass.velY = relaxation*mass.velY + mass.forceY/mass.mass() * delta_t;
            
        }
        
        var absorbList:  [Absorber] = [];
        
        var destroyList: [Destroyer] = [];
        
        var me: Int  = 0;
    
     
        for mass_me in g.masses {
            
            mass_me.forceX = 0;
            mass_me.forceY = 0;
            var them: Int = 0;
            for mass_them in g.masses {
                
                
                if (mass_me != mass_them) {
                    
                    let distX = mass_them.posX - mass_me.posX;
                    let distY = mass_them.posY - mass_me.posY;
                    
                    var dist = sqrt(pow(distX, 2) + pow(distY, 2));
                    
                    if (dist < (mass_them.radius + mass_me.radius)) {
                        dist = mass_them.radius + mass_me.radius;
                        if (mass_them.radius < mass_me.radius) {
                            var isInabsorblist: Bool = false;
                            for  abs in absorbList {
                                
                                if (abs.absorbs == me && abs.absorbs == them) {
                                    isInabsorblist = true;
                                    break;
                                }
                                
                            }
                            
                            if (!isInabsorblist) {
                                absorbList.append(Absorber(who: me, absorbs: them));
                                
                            }
                            
                        }
                        
                    }
                    
                  let force = GravC * mass_me.mass() * mass_them.mass() / (dist * dist);
                    
                    
                    mass_me.forceX = mass_me.forceX + force * (distX / dist);
                    mass_me.forceY = mass_me.forceY + force * (distY / dist);
                    
                }
                them += 1;
            }
            let distXEarth: Double = g.pnter.x - mass_me.posX;
            let distYEarth: Double = g.pnter.y - mass_me.posY;
           var distEarth = sqrt(pow(distXEarth,2) + pow(distYEarth,2));
            var forceEarth: Double
            if(distEarth>1) {
                forceEarth = GravC * mass_me.mass() * g.pnter.mass() / (distEarth * distEarth);
            }else{
                distEarth = 1;
                forceEarth = GravC * mass_me.mass() * g.pnter.mass() / (distEarth * distEarth);
            }
            mass_me.forceX = mass_me.forceX + forceEarth*(distXEarth/distEarth);
            mass_me.forceY = mass_me.forceY + forceEarth*(distYEarth/distEarth);
            mass_me.stayOnScreen();
            
            if(mass_me.radius > Double(g.windowWidth) * 0.3){
                destroyList.append(Destroyer(who: me))
            }
            
            me += 1;
        }
    
    
    
    
    
    for abs in absorbList {
    let prevMass = g.masses[abs.who].mass();
    let velInitialX = g.masses[abs.who].velX;
    let velInitialY = g.masses[abs.who].velY;
    
    g.masses[abs.who].radius = sqrt(pow(g.masses[abs.who].radius,2) + pow(g.masses[abs.absorbs].radius,2));
    
    g.masses[abs.who].velX = (g.masses[abs.who].velX*prevMass + g.masses[abs.absorbs].velX*g.masses[abs.absorbs].mass())/(prevMass + g.masses[abs.absorbs].mass());
    g.masses[abs.who].velY = (g.masses[abs.who].velY*prevMass + g.masses[abs.absorbs].velY*g.masses[abs.absorbs].mass())/(prevMass + g.masses[abs.absorbs].mass());
    
    g.masses[abs.who].forceX = g.masses[abs.who].forceX + 0.1*(g.masses[abs.who].mass()*(g.masses[abs.absorbs].velX-velInitialX))/delta_t;
    g.masses[abs.who].forceY = g.masses[abs.who].forceY + 0.1*(g.masses[abs.who].mass()*(g.masses[abs.absorbs].velY-velInitialY))/delta_t;
    
    g.masses.remove(at: abs.absorbs);
    g.spawnMass();
    
    }
    
    for de in  destroyList {
    // Log.d("TJG", "Destry loop de "+ Integer.toString(de.who));
    g.masses.remove(at: de.who);
    ///destroyPlayer.start();
    //g.destroyPlayer.start();
    }
    
    
    destroyList.removeAll()
    absorbList.removeAll()

    

        
        
    }
    
    
    
    
}

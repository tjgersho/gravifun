//
//  GravPhysics.swift
//  GraviFun
//
//  Created by Travis Gershon on 8/29/16.
//  Copyright © 2016 Travis Gershon. All rights reserved.
//

import Foundation
import AVFoundation


class GravPhysics {
    var destroyMassPlayer: AVAudioPlayer!
    static let instance = GravPhysics()
    private init(){print("Physics INIT")
   
        do {
            
            destroyMassPlayer = try AVAudioPlayer(contentsOfURL: NSURL(string: NSBundle.mainBundle().pathForResource("explosion", ofType: "mp3")!)!)
            
            destroyMassPlayer.prepareToPlay()
            destroyMassPlayer.numberOfLoops = 0
            
            
        }catch let err as NSError {
            print (err.debugDescription)
            
        }

    }
  //
    
    
   // func run ( masses: inout [Mass], delta_t: Double){
   func run (inout masses: [Mass], delta_t: Double){
       
        let windowWidth = Game.instance.windowWidth
        let windowHeight = Game.instance.windowHeight
        //let pnter = Game.instance.pnter
        
        
        let GravC: Double = 100*(Double(windowWidth)/1920.0)
        
        
        
        for mass in masses {
            
            mass.posX = mass.posX + delta_t*mass.velX;
            mass.posY = mass.posY + delta_t*mass.velY;
            
            var relaxation:Double = 1.0;
            if(sqrt(pow(mass.velX,2)+pow(mass.velX,2))*mass.mass() > 20){
                relaxation  = 0.98;
            }
            
            mass.velX = relaxation*mass.velX + mass.forceX/mass.mass() * delta_t;
            mass.velY = relaxation*mass.velY + mass.forceY/mass.mass() * delta_t;
            
        }
        
        var absorbList:  [Absorber] = []
        
        var destroyList: [Destroyer] = []
        
        var me: Int  = 0
    
     
        for mass_me in masses {
            
            mass_me.forceX = 0
            mass_me.forceY = 0
            var them: Int = 0
            for mass_them in masses {
                
                
                if (me != them) {
                    
                    let distX = mass_them.posX - mass_me.posX;
                    let distY = mass_them.posY - mass_me.posY;
                    
                    var dist = sqrt(pow(distX, 2) + pow(distY, 2));
                    
                    if (dist < (mass_them.radius + mass_me.radius)) {
                        dist = mass_them.radius + mass_me.radius
                        if (mass_them.radius < mass_me.radius) {
                            var isInabsorblist: Bool = false;
                            for  abs in absorbList {
                                
                                if (abs.absorbs == me || abs.absorbs == them) {
                                    isInabsorblist = true
                                    break
                                
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
                them += 1
            }
            
            
           let distXEarth:  Double = Game.instance.pnter.x - mass_me.posX;
            let distYEarth: Double = Game.instance.pnter.y - mass_me.posY;
            var distEarth: Double  =  sqrt(pow(distXEarth,2) + pow(distYEarth,2));
            var forceEarth: Double
            if(distEarth>1) {
                forceEarth = GravC * mass_me.mass() * Game.instance.pnter.mass(Game.instance.gravState) / (distEarth * distEarth);
            }else{
                distEarth = 1;
                forceEarth =  GravC * mass_me.mass() * Game.instance.pnter.mass(Game.instance.gravState) / (distEarth * distEarth);
            }
            mass_me.forceX = mass_me.forceX + forceEarth*(distXEarth/distEarth);
            mass_me.forceY = mass_me.forceY + forceEarth*(distYEarth/distEarth);
            mass_me.stayOnScreen(Double(windowWidth), winHeight: Double(windowHeight));
            
            if(mass_me.radius > Double(windowWidth) * 0.3){
                destroyList.append(Destroyer(who: me))
            }
            
            me += 1
        }
    

       
       
      absorbList.sortInPlace({$0.absorbs >  $1.absorbs})
    for abs in absorbList {
    let prevMass = masses[abs.who].mass();
    let velInitialX = masses[abs.who].velX;
    let velInitialY = masses[abs.who].velY;

    masses[abs.who].radius = sqrt(pow(masses[abs.who].radius,2) + pow(masses[abs.absorbs].radius,2));
   
    masses[abs.who].velX = (masses[abs.who].velX*prevMass + masses[abs.absorbs].velX*masses[abs.absorbs].mass())/(prevMass + masses[abs.absorbs].mass());
    masses[abs.who].velY = (masses[abs.who].velY*prevMass + masses[abs.absorbs].velY*masses[abs.absorbs].mass())/(prevMass + masses[abs.absorbs].mass());
    
    masses[abs.who].forceX = masses[abs.who].forceX + 0.1*(masses[abs.who].mass()*(masses[abs.absorbs].velX-velInitialX))/delta_t;
    masses[abs.who].forceY = masses[abs.who].forceY + 0.1*(masses[abs.who].mass()*(masses[abs.absorbs].velY-velInitialY))/delta_t;
        masses.removeAtIndex(abs.absorbs)
        
        let x: Double = Double(arc4random_uniform(100))/100.0 * Double(windowWidth)
        let y: Double = Double(arc4random_uniform(100))/100.0 * Double(windowHeight)
        
        masses.append(Mass(x: x, y: y, winWidth: windowWidth, winHeight: windowHeight));
       
    }
       
        
        //for abs in  absorbList{
             //       }
        //if absorbList.count > 1 {
       // for _ in 1..<absorbList.count {
            
            
        //}
        //}
        
    for de in  destroyList {
    // Log.d("TJG", "Destry loop de "+ Integer.toString(de.who));
    masses.removeAtIndex(de.who);
    destroyMassPlayer.play()
    //g.destroyPlayer.start();
    }
    
    
    destroyList.removeAll()
    absorbList.removeAll()
    
    
             
        
    }
    
    
    
    
}

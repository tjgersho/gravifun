//
//  Game.swift
//  GraviFun
//
//  Created by Travis Gershon on 8/29/16.
//  Copyright © 2016 Travis Gershon. All rights reserved.
//


import UIKit

enum GravState {
    case ISZERO
    case DARKENERGY
    case SINGULARITY
}

final class Game: NSObject {
    

    
    static let instance  = Game()
    
    var windowWidth: Int = 1000
    var windowHeight: Int = 1000
    
    var pnter = PointerGrav.instance
    
    var physics = GravPhysics()
    
    var masses: Array<Mass> = [Mass(x: 100,y: 100), Mass(x: 200,y: 200)]
    
 
    
    var gravState: GravState = GravState.ISZERO
    
    
    func update(dt: Double){
        print("Update Game Graph")
        
        physics.run(delta_t: dt)
        
    }
    
    func render(){
        print("Render")
        
        for mass in masses {
            mass.draw()
        }
    }
    
    func updateScreenSize(winWidth: Int, winHeight: Int){
        
        windowWidth = winWidth
        windowHeight  = winHeight
        
    }
    
    
    
    func addMass(x: Double, y: Double){
    self.masses.append(Mass(x: x, y: y))
      //self.spawnPlayer.start()
    }
    func clearMasses(){
      self.masses.removeAll()
    }
    
    func runballs(qty: Int) -> Int{
    //  Log.d("TJG", "Run Balls qty: " + Integer.toString(qty) );
     if(self.masses.count<qty){
        spawnMass();
      return runballs(qty: qty);
      }else{
    
       return 1;
      }
    
    }
    
    func spawnMass(){
        let x: Double = Double(arc4random_uniform(100)/100) * Double(windowWidth)
        let y: Double = Double(arc4random_uniform(100)/100) * Double(windowHeight)
     
        masses.append(Mass(x: x, y: y));
    }
    
}

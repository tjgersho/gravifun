//
//  Game.swift
//  GraviFun
//
//  Created by Travis Gershon on 8/29/16.
//  Copyright © 2016 Travis Gershon. All rights reserved.
//


import UIKit


final class Game: NSObject {
    
    static let instance  = Game()
    private override init(){}
    
    
    var windowWidth: Int = 1000
    var windowHeight: Int = 1000
    var clearMasses: Bool = false
    
    var runMasses: Int = 0
    
    
   var pnter = PointerGrav.instance
    
    
    var masses: Array<Mass> = []
 
    
    var gravState: Int = 0
    
  
    func update(dt: Double){
       // print("Update Game Graph")
        if(clearMasses){
            masses.removeAll()
            clearMasses = false
        }
        
        if(runMasses > 0) {
         runballs(runMasses)
            runMasses = 0
        }
        
        GravPhysics.instance.run(&masses, delta_t: dt)
        
    }
    
    func render(){
        //print("Render")
        
        for mass in masses {
            mass.draw()
        }
        
        pnter.draw()
    }
    
    func updateScreenSize(winWidth: Int, winHeight: Int){
        print("Width \(winWidth)")
        print("Height \(winHeight)")
        
        windowWidth = winWidth
        windowHeight  = winHeight
        
    }
    
    
    
    func addMass(x: Double, y: Double){
    self.masses.append(Mass(x: x, y: y, winWidth: windowWidth, winHeight: windowHeight))
      //self.spawnPlayer.start()
    }
    
    
    func runballs(qty: Int) -> Int{
     
     if(self.masses.count<qty){
        spawnMass();
     return runballs(qty);
      }else{
    
      return 1
      }
    
    }
    
    func spawnMass(){
        let x: Double = Double(arc4random_uniform(100))/100.0 * Double(windowWidth)
        let y: Double = Double(arc4random_uniform(100))/100.0 * Double(windowHeight)
         
        
        masses.append(Mass(x: x, y: y, winWidth: windowWidth, winHeight: windowHeight));
    }
    
}

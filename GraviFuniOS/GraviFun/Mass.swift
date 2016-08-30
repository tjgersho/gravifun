//
//  Mass.swift
//  GraviFun
//
//  Created by Travis Gershon on 8/29/16.
//  Copyright © 2016 Travis Gershon. All rights reserved.
//

import UIKit
//import Foundation
//import QuartzCore

class Mass: NSObject{
    
    var posX: Double
    var posY: Double
    var velX: Double = 0.0
    var velY: Double = 0.0
    var radius: Double

    
    var forceX: Double = 0
    var forceY: Double = 0
    
    var color:CGColor!
    //var g = Game.instance
    
    var windowWidth: Int
    var windowHeight: Int
    
    init(x:Double, y:Double, winWidth: Int, winHeight: Int){
        self.posX = x
        self.posY = y
        self.windowWidth = winWidth
        self.windowHeight = winHeight
        
         let size:Double = Double(min(windowWidth, windowHeight))
        
         // print(self.forceX)
        
        self.radius =  Double(arc4random_uniform(100))/100.0*size*0.005 + size*0.002
       
        
        //print("Radius \(radius) ")
        
        
       
        let red = Double(arc4random()%256)/256.0
        let green = Double(arc4random()%256)/256.0
        let blue = Double(arc4random()%256)/256.0
        //print("\(red)")
        self.color = UIColor(red: CGFloat(red), green: CGFloat(green), blue: CGFloat(blue), alpha: 255).cgColor
        
        
    }
    
    func mass() -> Double{
        let size:Double = Double(min(windowWidth, windowHeight))
       
        let mass = 0.5*self.radius*self.radius*Double.pi*(1920/size)
               
        
        return mass
    }
    
    func draw(){
       
        if  let context = UIGraphicsGetCurrentContext() {
            
            // print("DRAW Mass")
            
          //  context.setLineWidth(1.0)
            context.setFillColor(self.color)
            
            
            // context.setFillColor(color)
            let sq = CGRect(x: CGFloat(self.posX-self.radius), y: CGFloat(self.posY-self.radius), width: CGFloat(2*self.radius), height: CGFloat(2*self.radius))
            
            context.addEllipse(inRect: sq)
            context.fillPath()
            //context.fillEllipse(in: sq)
            // context.fillPath()
            
            
        }
    }
    
    
    
    
    func stayOnScreen(winWidth: Double, winHeight: Double){
    
        if(self.posX+self.radius>winWidth){
            self.posX = winWidth - self.radius;
            self.velX = -self.velX;
            self.forceX = -self.forceX;
        }
        if((self.posX-self.radius) < 0.0)
        {
            self.posX = self.radius;
            self.velX = -self.velX;
            self.forceX = -self.forceX;
        }
        if (self.posY+self.radius > winHeight){
            self.posY = winHeight - self.radius;
            self.velY = -self.velY;
            self.forceY = -self.forceY;
            
        }
        if(self.posY-self.radius<0){
            self.posY = self.radius;
            self.velY = -self.velY;
            self.forceY = -self.forceY;
        }
    
    }
    
}

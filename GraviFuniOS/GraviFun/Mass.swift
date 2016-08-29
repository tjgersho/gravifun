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
    var diameter: Double
    
    var forceX: Double = 0
    var forceY: Double = 0
    
    var color:CGColor!
    var g = Game.instance
    
    
    init(x:Double, y:Double){
        self.posX = x
        self.posY = y
        
        let size:Double = Double(min(g.windowWidth, g.windowHeight))
        
        
        self.radius = Double(arc4random_uniform(100)/100)*size*0.005 + size*0.002
        self.diameter  = 2*self.radius;
        
        let red = Double(arc4random()%256)/256.0
        let green = Double(arc4random()%256)/256.0
        let blue = Double(arc4random()%256)/256.0
        print("\(red)")
        self.color = UIColor(red: CGFloat(red), green: CGFloat(green), blue: CGFloat(blue), alpha: 255).cgColor
        
        
    }
    
    func mass() -> Double{
        let size:Double = Double(min(g.windowWidth, g.windowHeight))
        
        return 0.5*self.radius*self.radius*Double.pi*(1920/size);
    }
    
    func draw(){
        
        if  let context = UIGraphicsGetCurrentContext() {
            
            
            
            context.setLineWidth(1.0)
            context.setFillColor(self.color)
            
            
            // context.setFillColor(color)
            let sq = CGRect(x: CGFloat(self.posX), y: CGFloat(self.posY), width: CGFloat(100), height: CGFloat(100))
            
            context.addEllipse(inRect: sq)
            context.fillPath()
            //context.fillEllipse(in: sq)
            // context.fillPath()
            
            
        }
    }
    
    
    
    
    func stayOnScreen(){
    
        if(self.posX+self.radius>Double(g.windowWidth)){
            self.posX = Double(g.windowWidth) - self.radius;
            self.velX = -self.velX;
            self.forceX = -self.forceX;
        }
        if((self.posX-self.radius) < 0.0)
        {
            self.posX = self.radius;
            self.velX = -self.velX;
            self.forceX = -self.forceX;
        }
        if (self.posY+self.radius > Double(g.windowHeight)){
            self.posY = Double(g.windowHeight) - self.radius;
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

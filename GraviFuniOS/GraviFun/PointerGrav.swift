//
//  Click.swift
//  GraviFun
//
//  Created by Travis Gershon on 8/29/16.
//  Copyright © 2016 Travis Gershon. All rights reserved.
//
import UIKit


class PointerGrav: NSObject {
    
    static let instance  = PointerGrav()
    private override init(){}
    


    
    var x:Double = 0
    var y:Double = 0
    
    
    
    func mass(gravstate: Int) -> Double{
    
    
        if(gravstate == 0) {
            return 0.0001;
        }
        
        if(gravstate == 1){
            return 10000;
        }
        
        if(gravstate == 2){
            return -10000;
        }else{
            
            return 0.0001;
        }
    
    }
    
    func draw(){
        
        if  let context = UIGraphicsGetCurrentContext() {
            
           // print("DRAW Pointer")
            
            context.setLineWidth(3.0)
            context.setStrokeColor(UIColor(red: CGFloat(1), green: CGFloat(1), blue: CGFloat(1), alpha: 1).cgColor)
            
            
            // context.setFillColor(color)
            let sq = CGRect(x: CGFloat(self.x-6), y: CGFloat(self.y-6), width: CGFloat(12), height: CGFloat(12))
            
            context.addEllipse(inRect: sq)
            
            context.strokePath()
            //context.fillEllipse(in: sq)
            // context.fillPath()
            
            
        }
    }
    
}

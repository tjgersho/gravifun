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
    
    let g = Game.instance

    
    var x:Double = 0
    var y:Double = 0
    
    
    
    func mass() -> Double{
    
    
        if(g.gravState == GravState.ISZERO) {
            return 0.0001;
        }
        
        if(g.gravState == GravState.SINGULARITY){
            return 20000;
        }
        
        if(g.gravState == GravState.DARKENERGY){
            return -20000;
        }else{
            
            return 0.0001;
        }
    
    }
    
}

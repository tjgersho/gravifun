//
//  GameView.swift
//  GraviFun
//
//  Created by Travis Gershon on 8/29/16.
//  Copyright © 2016 Travis Gershon. All rights reserved.
//

import UIKit

class GameView: UIView {
    
    let g = Game.instance
    
    var lastTime = CACurrentMediaTime();
    var delta_t: Double = 0.0
    var now:Double = 0.0
    var linelength:Double = 0;
    // Only override draw() if you perform custom drawing.
    // An empty implementation adversely affects performance during animation.
    override func draw(_ rect: CGRect) {
        // Drawing code
        
        
        self.backgroundColor = UIColor.black
        UIColor.black.set()
        UIRectFill(rect)

        
        let now = CACurrentMediaTime();
        let delta_t = now - lastTime
        //drawLine(delta_t: delta_t)
        
        g.update(dt: delta_t)
        g.render()
        
        lastTime = now
    }
    
 
    
}

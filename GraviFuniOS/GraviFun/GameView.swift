//
//  GameView.swift
//  GraviFun
//
//  Created by Travis Gershon on 8/29/16.
//  Copyright © 2016 Travis Gershon. All rights reserved.
//

import UIKit

class GameView: UIView {
    
 
    
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
      //  print("Delta T \(delta_t)")
     
         Game.instance.update(dt: delta_t)
        Game.instance.render()
        
        drawButtons()
        
        
        lastTime = now
    }
    
    
    private func drawButtons(){
    
        let windowWidth: Double = Double(self.frame.size.width)
   let windowHeight: Double = Double(self.frame.size.height)
    let btnheight = 0.10*windowHeight;
    let topbtnwidth = 0.25*windowWidth;
    let bottmbtnwidth = windowWidth/3;
    
    let topbtny = 0;
    let grav0origx = 0;
    let grav10origx = topbtnwidth;
    let grav50origx = 2*topbtnwidth;
    let grav100origx = 3*topbtnwidth;
    
    let bottmbtny = windowHeight-btnheight;
    
    let darkorigx = 0;
    let zeroorigx = bottmbtnwidth;
    let singorigx = 2*bottmbtnwidth;
    
      
        drawBtn(txt: "Singularity" ,  colorbtn: UIColor(red: 0.392, green: 0.392, blue: 0.392, alpha: 0.392).cgColor, cx: singorigx, cy: bottmbtny, rectwidth: bottmbtnwidth, rectheight: btnheight);
        
    drawBtn(txt: "Dark Energy", colorbtn: UIColor(red: 0.392, green: 0.196, blue: 0.392, alpha: 0.392).cgColor, cx: Double(darkorigx), cy: bottmbtny, rectwidth: bottmbtnwidth, rectheight: btnheight);
        
    drawBtn(txt: "Zero Mass",   colorbtn: UIColor(red: 0.392, green: 0.196, blue: 0.196, alpha: 0.392).cgColor, cx: zeroorigx, cy: bottmbtny, rectwidth: bottmbtnwidth, rectheight: btnheight);
    
    drawBtn(txt: "Grav-0",   colorbtn: UIColor(red: 0.392, green: 0.196, blue: 0.196, alpha: 0.392).cgColor, cx: Double(grav0origx), cy: Double(topbtny), rectwidth: topbtnwidth, rectheight: btnheight);
    drawBtn(txt: "Grav-10",   colorbtn: UIColor(red: 0.392, green: 0.196, blue: 0.196, alpha: 0.196).cgColor, cx: grav10origx, cy: Double(topbtny), rectwidth: topbtnwidth, rectheight: btnheight);
    drawBtn(txt: "Grav-50",  colorbtn: UIColor(red: 0.392, green: 0.392, blue: 0.784, alpha: 0.392).cgColor, cx: grav50origx, cy: Double(topbtny), rectwidth: topbtnwidth, rectheight: btnheight);
    drawBtn(txt: "Grav-100",   colorbtn: UIColor(red: 0.392, green: 0.784, blue: 0.196, alpha: 0.392).cgColor, cx: grav100origx, cy: Double(topbtny), rectwidth: topbtnwidth, rectheight: btnheight);
    
    
    }
    
    private func drawBtn(txt: String, colorbtn: CGColor, cx: Double,
                         cy: Double, rectwidth: Double, rectheight: Double){
        
        if  let context = UIGraphicsGetCurrentContext() {
            

            context.setFillColor(colorbtn)
            context.addRect(CGRect(x: cx, y: cy, width: rectwidth, height: rectheight))
            context.fillPath()
  
    
     }
    }
 
    
}




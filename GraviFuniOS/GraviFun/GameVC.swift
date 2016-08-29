//
//  GameVC.swift
//  GraviFun
//
//  Created by Travis Gershon on 8/29/16.
//  Copyright © 2016 Travis Gershon. All rights reserved.
//


import UIKit

class GameVC: UIViewController {
    
       
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
       Game.instance.updateScreenSize(winWidth: Int(self.view.frame.size.width), winHeight: Int(self.view.frame.size.height))
        
        let gl = GameLoop(frameInterval: 40, runFunc: runGame)
        gl.start()
        
        print("GAMEVC LOADED")
        
         Game.instance.runballs(qty: 30);
    }
    
    func runGame(){
       // print("LOOP")
        
        self.view.setNeedsDisplay()
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        let touch = touches.first?.location(in: self.view)
        
        
        //print("\(touch?.x)")
        
      //  print("\(touch?.y)")
        
        let touchX: Double = Double((touch?.x)!)
        let touchY: Double = Double((touch?.y)!)
        
        Game.instance.pnter.x = touchX
         Game.instance.pnter.y = touchY
        
        Game.instance.addMass(x: touchX, y: touchY)
      // print("\(g.pnter.x)")
        
      //  print("\(g.pnter.y)")
    }
}


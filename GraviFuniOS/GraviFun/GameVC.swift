//
//  GameVC.swift
//  GraviFun
//
//  Created by Travis Gershon on 8/29/16.
//  Copyright © 2016 Travis Gershon. All rights reserved.
//


import UIKit

class GameVC: UIViewController {
    let g = Game.instance
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        g.updateScreenSize(winWidth: Int(self.view.frame.size.width), winHeight: Int(self.view.frame.size.height))
        
        let gl = GameLoop(frameInterval: 10, runFunc: runGame)
        gl.start()
        
        print("GAMEVC LOADED")
    }
    
    func runGame(){
        print("LOOP")
        
        self.view.setNeedsDisplay()
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        let touch = touches.first?.location(in: self.view)
        
        
        print("\(touch?.x)")
        
        print("\(touch?.y)")
        
        g.pnter.x = Double((touch?.x)!)
        g.pnter.y = Double((touch?.y)!)
        
        
        print("\(g.pnter.x)")
        
        print("\(g.pnter.y)")
    }
}


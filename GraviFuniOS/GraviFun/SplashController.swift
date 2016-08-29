//
//  ViewController.swift
//  GraviFun
//
//  Created by Travis Gershon on 8/29/16.
//  Copyright © 2016 Travis Gershon. All rights reserved.
//

import UIKit

class SplashController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
      Timer.scheduledTimer(withTimeInterval: 0.5, repeats: false, block: { _ in self.startGame() })
    }
    
    func startGame(){
        print("Start Game")
        
        performSegue(withIdentifier: "GoGameStart", sender: nil)
    
    }
    
    

}


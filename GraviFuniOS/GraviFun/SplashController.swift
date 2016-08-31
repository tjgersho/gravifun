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
        
        if #available(iOS 10.0, *) {
            Timer.scheduledTimer(withTimeInterval: 2, repeats: false, block: { _ in self.startGame() })
        } else {
            Timer.scheduledTimer(timeInterval: 2, target: self, selector: #selector(self.startGame), userInfo: nil, repeats: false)
            
        }
    }
    
    func startGame(){
        print("Start Game")
        
        performSegue(withIdentifier: "GoGameStart", sender: nil)
    
    }
    
    

}


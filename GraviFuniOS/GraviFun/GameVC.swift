//
//  GameVC.swift
//  GraviFun
//
//  Created by Travis Gershon on 8/29/16.
//  Copyright © 2016 Travis Gershon. All rights reserved.
//


import UIKit
import AVFoundation


class GameVC: UIViewController {
    
    var windowWidth: Int = 1000
    var windowHeight: Int = 1000
    
    static var instance = GameVC()
    
    var musicPlayer: AVAudioPlayer!
    var addMassPlayer: AVAudioPlayer!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let orentation = UIDevice.current.orientation
        
        
        if(orentation.isPortrait){
            
            windowWidth  = Int(self.view.frame.size.width)
            windowHeight  = Int(self.view.frame.size.height)
        }else{
            windowHeight  = Int(self.view.frame.size.width)
            windowWidth  = Int(self.view.frame.size.height)
        }
        
        
     
       Game.instance.updateScreenSize(winWidth: windowWidth, winHeight: windowHeight)
        
       // let gl = GameLoop(frameInterval: 60, runFunc: runGame)
        //gl.start()
        
        if #available(iOS 10.0, *) {
            Timer.scheduledTimer(withTimeInterval: 0.05, repeats: true, block: { _ in self.runGame() })
        } else {
            Timer.scheduledTimer(timeInterval: 0.05, target: self, selector: #selector(self.runGame), userInfo: nil, repeats: true)
            
        }
        
        
        print("GAMEVC LOADED")
        
         Game.instance.runballs(qty: 30)
         initAudio()
        
       
    }
    func initAudio(){
        
        let path = Bundle.main.path(forResource: "lightyears", ofType: "mp3")!
        
        do {
            musicPlayer = try AVAudioPlayer(contentsOf: URL(string: path)!)
            musicPlayer.prepareToPlay()
            musicPlayer.numberOfLoops = -1
            musicPlayer.play()
            
            addMassPlayer = try AVAudioPlayer(contentsOf: URL(string: Bundle.main.path(forResource: "bottlepop", ofType: "wav")!)!)
            addMassPlayer.prepareToPlay()
            addMassPlayer.numberOfLoops = 1
            
            
        }catch let err as NSError {
            print (err.debugDescription)
            
        }
    }
    func stopMusic(){
        print("StopMusic Called App")
        if musicPlayer.isPlaying   {
            musicPlayer.stop()
        }
        
    }
    override func viewDidDisappear(_ animated: Bool) {
    super.viewDidDisappear(animated)
        print("Leaving App")
        musicPlayer.stop()
    }
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        print("Leaving App")
        musicPlayer.stop()
    }
    
    override func viewWillTransition(to size: CGSize, with coordinator: UIViewControllerTransitionCoordinator) {
       
        let orentation = UIDevice.current.orientation

        
        if(orentation.isPortrait && self.view.frame.size.width < self.view.frame.size.height){
                       windowWidth  = Int(self.view.frame.size.width)
                       windowHeight  = Int(self.view.frame.size.height)
                    }else{
           
                windowHeight  = Int(self.view.frame.size.width)
                windowWidth  = Int(self.view.frame.size.height)
                
        
        }
    
        
     
       
        Game.instance.updateScreenSize(winWidth: windowWidth, winHeight: windowHeight)
        
        
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
        
        
        let btn: Int = whichButton(clickX: touchX, clickY: touchY)
        print("Button Click \(btn)")
        if(btn != 0){
            
            switch (btn){
            case 1:
                Game.instance.clearMasses = true;
                Game.instance.runMasses = 0
                
                break
            case 2:
                Game.instance.clearMasses = true
                Game.instance.runMasses = 10
                
                break
            case 3:
                Game.instance.clearMasses = true
                Game.instance.runMasses = 50
                
                break
            case 4:
                Game.instance.clearMasses = true
                Game.instance.runMasses = 100
                break
            case 5:
               Game.instance.gravState = 2
               break
            case 6:
                Game.instance.gravState = 0
                break
            case 7:
               Game.instance.gravState = 1
                break
            default: break
                
            }
            
            
        }else{
            
            addMassPlayer.play()
            
             Game.instance.addMass(x: touchX, y: touchY)
            Game.instance.pnter.x = touchX
            Game.instance.pnter.y = touchY

            
            
        }
        
    }
    
    
    private func whichButton(clickX: Double, clickY: Double) -> Int{
        if(clickY > 0.10*Double(windowHeight) && clickY < (Double(windowHeight)-0.10*Double(windowHeight))){
            return 0
        }else{
            
            if(clickY < 0.10*Double(windowHeight)){  /// Is Grav-#
                
                if(clickX < Double(windowWidth)/4){
                    return 1;
                }else if(clickX > Double(windowWidth)/4 && clickX < Double(windowWidth)/2){
                    return 2;
                }else if(clickX > Double(windowWidth)/2 && clickX < Double(3/4*Double(windowWidth))){
                    return 3;
                }else{
                    return 4;
                }
                
                
            }else{  // is Pointer Mass Setting
                
                if(clickX < Double(windowWidth)/3) {
                    return 5;
                }else if(clickX > Double(windowWidth)/3 && clickX <  Double( 2/3*Double(windowWidth))) {
                    return 6;
                }else{
                    return 7;
                }
                
            }
            
        }
        
     
    }
    
    
    
}


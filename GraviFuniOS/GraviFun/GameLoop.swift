//
//  GameLoop.swift
//  GraviFun
//
//  Created by Travis Gershon on 8/29/16.
//  Copyright © 2016 Travis Gershon. All rights reserved.
//


import UIKit

class GameLoop : NSObject {
    
    var run: () -> ()!
    var displayLink : CADisplayLink!
    var frameInterval : Int!
    
    init(frameInterval: Int, runFunc: () -> ()) {
        self.run = runFunc
        self.frameInterval = frameInterval
        super.init()
        
    }
    
    // you could overwrite this too
    func handleTimer() {
        run()!
    }
    
    func start() {
        displayLink = CADisplayLink(target: self, selector: #selector(GameLoop.handleTimer))
        if #available(iOS 10.0, *) {
            displayLink.preferredFramesPerSecond = frameInterval
        } else {
           displayLink.frameInterval = frameInterval
        }
        displayLink.add(to: RunLoop.main, forMode: RunLoopMode(rawValue: RunLoopMode.commonModes.rawValue))
    }
    
    func stop() {
        displayLink.isPaused = true
        displayLink.remove(from: RunLoop.main, forMode: RunLoopMode(rawValue: RunLoopMode.defaultRunLoopMode.rawValue))
        displayLink = nil
    }
}

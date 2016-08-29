//
//  Absorber.swift
//  GraviFun
//
//  Created by Travis Gershon on 8/29/16.
//  Copyright © 2016 Travis Gershon. All rights reserved.
//

import Foundation


class Absorber: NSObject {
    
    var who: Int
    var absorbs: Int
    init ( who: Int, absorbs: Int){
        self.who = who;
        self.absorbs = absorbs;
    }
}

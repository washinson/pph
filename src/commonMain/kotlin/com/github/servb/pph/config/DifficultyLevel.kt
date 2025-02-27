package com.github.servb.pph.config

import com.github.servb.pph.pheroes.common.common.MineralSet

class DifficultyLevel{

    var v: Int
    var human: MineralSet?
    var computer: MineralSet?

    constructor(yaml: LinkedHashMap<String, Any?>){
        v = yaml["v"] as Int
        human = createMineralSet(yaml["human"] as LinkedHashMap<String, Any?>?)
        computer = createMineralSet(yaml["computer"] as LinkedHashMap<String, Any?>?)
    }
}


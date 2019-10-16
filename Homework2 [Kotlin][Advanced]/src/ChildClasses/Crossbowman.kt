package ChildClasses

import Abstract.Defender

class Crossbowman(
    power: Double
) : Defender(power){

    override fun shoot(): Double = power * 1.5

}
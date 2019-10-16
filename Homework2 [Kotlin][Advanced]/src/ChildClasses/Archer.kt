package ChildClasses

import Abstract.Defender

class Archer (
    power: Double
) : Defender(power){

    override fun shoot(): Double = power

}
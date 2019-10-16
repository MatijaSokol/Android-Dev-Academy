package ChildClasses

import Abstract.Enemy

class WeakEnemy (
    health: Double,
    power: Double,
    private var specialPowerUsed: Boolean = false
) : Enemy(health, power){

    override fun useSpecialPower() {
        if (!specialPowerUsed)  power *= 1.3
        specialPowerUsed = true
    }
}
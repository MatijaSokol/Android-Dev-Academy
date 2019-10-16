package ChildClasses

import Abstract.Enemy
import Interface.ICurable

class HardEnemy (
    health: Double,
    power: Double,
    private var specialPowerUsed: Boolean = false,
    private var cured: Boolean = false
) : Enemy(health, power), ICurable {

    override fun useSpecialPower() {
        if (!specialPowerUsed)  power *= 2
        specialPowerUsed = true
    }

    override fun increaseHealth(points: Double){
        if (!cured) health += points
        cured = true
    }

}
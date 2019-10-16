package Abstract

abstract class Enemy (
    protected var health : Double,
    protected var power : Double
){
    fun isAlive():Boolean = health > 0

    fun gethealth(): Double = health

    fun takeHit(points: Double){
        if (health - points < 0) health = 0.0
        else health -= points
        if (!isAlive()) power = 0.0
    }

    fun attack(): Double = power

    abstract fun useSpecialPower()
}
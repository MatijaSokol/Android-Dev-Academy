package Abstract

abstract class Defender (
    protected var power: Double
) {
    abstract fun shoot(): Double
    fun removePower(){
        power = 0.0
    }
}
package General

import Abstract.Defender
import ChildClasses.Archer
import ChildClasses.Crossbowman
import Main.isEven

class Castle (
    var strength: Double,
    var defenders: MutableList<Defender>
){
    fun isAlive():Boolean = strength > 0

    fun takeHit(points: Double){
        if (strength - points < 0)  strength = 0.0
        else strength -= points
        if (!isAlive()) removeDefenders()
    }

    private fun removeDefenders() = defenders.forEach { it.removePower() }

    fun repairWalls(){
        val probability = (1..100).random()
        when(probability){
            in 1..10 -> strength += (50..100).random()
        }
    }

    companion object {
        fun makeCastle(difficulty: String?, numberOfDefenders: Int): Castle {
            val defenders = mutableListOf<Defender>()
            var strength = 0.0

            when(difficulty){
                "1" -> {
                    for (i in 1..numberOfDefenders) {
                        if (i.isEven()) defenders.add(Archer((0..15).random().toDouble()))
                        else defenders.add(Crossbowman((0..13).random().toDouble()))
                    }
                    strength = 1000.0
                }
                "2" -> {
                    for (i in 1..numberOfDefenders) {
                        if (i.isEven()) defenders.add(Archer((0..14).random().toDouble()))
                        else defenders.add(Crossbowman((0..12).random().toDouble()))
                    }
                    strength = 950.0
                }
                "3" -> {
                    for (i in 1..numberOfDefenders) {
                        if (i.isEven()) defenders.add(Archer((0..13).random().toDouble()))
                        else defenders.add(Crossbowman((0..11).random().toDouble()))
                    }
                    strength = 900.0
                }
            }

            return Castle(strength, defenders)
        }
    }


}
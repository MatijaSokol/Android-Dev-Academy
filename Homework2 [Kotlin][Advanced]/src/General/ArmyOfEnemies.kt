package General

import Abstract.Enemy
import ChildClasses.HardEnemy
import ChildClasses.MediumEnemy
import ChildClasses.WeakEnemy
import Interface.ICurable

class ArmyOfEnemies(
    private var army: List<Enemy>
) {
    companion object {
        fun makeEnemies(difficulty: String?, numberOfEnemies: Int): ArmyOfEnemies {
            val enemies = mutableListOf<Enemy>()

            when(difficulty){
                "1" -> {
                    for(i in 1..numberOfEnemies){
                        val probability = (1..100).random()
                        when(probability){
                            in 1..40 -> enemies.add(WeakEnemy(100.0, 1.0))
                            in 41..75 -> enemies.add(MediumEnemy(100.0, 2.0))
                            in 76..100 -> enemies.add(HardEnemy(100.0, 3.0))
                        }
                    }
                }
                "2" -> {
                    for(i in 1..numberOfEnemies){
                        val probability = (1..100).random()
                        when(probability){
                            in 1..33 -> enemies.add(WeakEnemy(100.0, 1.0))
                            in 34..66 -> enemies.add(MediumEnemy(100.0, 2.0))
                            in 67..100 -> enemies.add(HardEnemy(100.0, 3.0))
                        }
                    }
                }
                "3" -> {
                    for(i in 1..numberOfEnemies){
                        val probability = (1..100).random()
                        when(probability){
                            in 1..30 -> enemies.add(WeakEnemy(100.0, 1.0))
                            in 31..62 -> enemies.add(MediumEnemy(100.0, 2.0))
                            in 63..100 -> enemies.add(HardEnemy(100.0, 3.0))
                        }
                    }
                }
            }

            return ArmyOfEnemies(enemies)
        }
    }

    fun getArmy(): List<Enemy> = army

    fun isDead(): Boolean{
        army.forEach { if (it.isAlive())   return false }
        return true
    }

    fun getEnemiesHealth(): MutableList<Double>{
        val enemiesHealth = mutableListOf<Double>()
        army.forEach { enemiesHealth.add(it.gethealth()) }
        return enemiesHealth
    }

    fun useSpecialPower(){
        army.forEach {
            val probability = (1..100).random()
            when(probability){
                in 1..25 -> it.useSpecialPower()
            }
        }
    }

    fun getRandomAliveEnemy(): Int{
        var randomPosition = (0 until army.size).random()

        while (!army[randomPosition].isAlive()){
            randomPosition = (0 until army.size).random()
        }

        return randomPosition
    }

    fun useIncreaseHealth(){
        army.forEach {
            if (it is ICurable){
                val probability = (1..100).random()
                when(probability){
                    in 1..15 -> it.increaseHealth((0..5).random().toDouble())
                }
            }
        }
    }
}
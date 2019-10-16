package Main

import General.ArmyOfEnemies
import General.Castle

fun Int.isEven(): Boolean {
    return this % 2 == 0
}

const val NUMBER_OF_ATTACKS = 20
const val NUMBER_OF_ENEMIES = 30
const val NUMBER_OF_DEFENDERS = 30

fun main() {
    var runGame: String

    do {
        var difficulty: String
        var defended = false

        do {
            println("Select difficulty")
            println("[1] for easy   [2] for medium  [3] for hard")
            difficulty = readLine() ?: ""
        } while (difficulty != "1" && difficulty != "2" && difficulty != "3")

        val castle = Castle.makeCastle(difficulty, NUMBER_OF_DEFENDERS)
        val armyOfEnemies = ArmyOfEnemies.makeEnemies(difficulty, NUMBER_OF_ENEMIES)

        for(i in 1..NUMBER_OF_ATTACKS){
            castle.takeHit(armyOfEnemies.getArmy().sumByDouble { it.attack() })

            for (j in 0 until armyOfEnemies.getArmy().size){
                if (armyOfEnemies.isDead()){
                    println("Enemies Dead --- General.Castle Defended")
                    defended = true
                    break
                }
                if (!armyOfEnemies.getArmy()[i].isAlive())  {
                    val random = armyOfEnemies.getRandomAliveEnemy()
                    armyOfEnemies.getArmy()[random].takeHit(castle.defenders[i].shoot())
                }
                else armyOfEnemies.getArmy()[i].takeHit(castle.defenders[i].shoot())
            }

            if (defended) {
                println("General.Castle strength: ${castle.strength}")
                println("Enemies health: ${armyOfEnemies.getEnemiesHealth()}")
                break
            }

            armyOfEnemies.useSpecialPower()
            armyOfEnemies.useIncreaseHealth()
            castle.repairWalls()

            println("General.Castle strength: ${castle.strength}")
            println("Enemies health: ${armyOfEnemies.getEnemiesHealth()}")

            if (!castle.isAlive())  {
                println("General.Castle Destroyed --- Enemies Won")
                break
            }

            if (armyOfEnemies.isDead()){
                println("Enemies Dead --- General.Castle Defended")
                break
            }
        }

        println("Press [y] for new game")
        runGame = readLine() ?: ""
    } while (runGame == "y")
}
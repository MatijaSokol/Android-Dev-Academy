package com.example.jamb.model

class DiceRoller {
    private val dice: MutableList<Die>

    init {
        dice = retrieveDice()
    }

    private fun retrieveDice(): MutableList<Die>{
        return mutableListOf(
            Die(6),
            Die(6),
            Die(6),
            Die(6),
            Die(6),
            Die(6)
        )
    }

    fun getRollResults(): MutableList<Int>{
        val listOfResults = mutableListOf<Int>()
        dice.forEach { listOfResults.add(it.rollDie()) }
        return listOfResults
    }

}
package com.example.jamb.model

class Die (
    private val numberOfSides: Int
){
    fun rollDie(): Int = (1..numberOfSides).random()
}
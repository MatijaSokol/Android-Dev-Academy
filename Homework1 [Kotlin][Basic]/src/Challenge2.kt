package exercises

/*
Create a collection of integers

Use Random to fill the collection with 100 random numbers between 1 and 100.

Go through the collection from start to end and print its elements up to the point where an element is less than or equal to 10
 */

fun main() {
    val size = 100
    val list = mutableListOf<Int>()

    for (i in 0..(size-1)) {
        list.add((1..size).random())
        print(list[i].toString() + " ")
    }

    println()

    var counter = 0
    while(list[counter] <= 10) {
        print(list[counter].toString() + " ")
        counter++
    }

    println()

    when (counter){
        1 -> println("$counter element in new array")
        else -> println("$counter elements in new array")
    }
}

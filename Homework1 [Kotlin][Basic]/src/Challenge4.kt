package exercises

/*
Challenge: Apply Functional Programming for Simple Data Analysis

We decided to gather data on the age of our users.
In this challenge, you'll be presented with this partly faulty data
 of user ages which is based on four input files:
 */

val data = mapOf(
    "users1.csv" to listOf(32, 45, 17, -1, 34),
    "users2.csv" to listOf(19, -1, 67, 22),
    "users3.csv" to listOf(),
    "users4.csv" to listOf(56, 32, 18, 44)
)

/*
Apply the functions you learned about as well as other functions from Kotlin's
standard library to solve the following data analysis tasks:

1. Find the average age of users (use only valid ages for the calculation!)*/

fun getAverageAge() :Double{
    val countOfValidAges = data.flatMap { it.value }.count { it > 0 }
    val sumOfValidAges = data.flatMap { it.value }.filter { it > 0 }.sum()

    var average = sumOfValidAges.toDouble() / countOfValidAges.toDouble()
    average = Math.round(average * 1000.0) / 1000.0
    return average
}

/*
2. Extract the names of input files that contain faulty data*/

fun getIncorrectFiles(): Set<String> {
    val list = mutableSetOf<String>()

    for ((key, value) in data){
        value.forEach() {
            if (it <= 0){
                list.add(key)
            }
        }
    }

    return list
}

/*
3. Count the total number of faulty entries across all input files*/

fun getCountOfIncorrectEntries(): Int{
    return data.flatMap { it.value }.filter { it <=0 }.count()
}

/*
Hints: map() and flatMap() are often very useful functions for such tasks
Use IntelliJ's autocompletion to explore which other functions, that were not
presented in the lectures, are available (they could simplify the tasks)
 */

fun main() {
    println(getAverageAge())
    println(getIncorrectFiles())
    println(getCountOfIncorrectEntries())
}
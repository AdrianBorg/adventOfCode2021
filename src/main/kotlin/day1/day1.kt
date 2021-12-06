package day1

import util.readResourcesToIntList

fun main() {

    day1a()

    day1b()
}



fun countArrayElementIncreases(elements: List<Int>): Int {
    var prevElement = elements[0]
    var count = 0

    for (element in elements){
        if (element > prevElement) {
            count++
        }
        prevElement = element
    }

    return count
}

fun day1b() {
    val measurements = readResourcesToIntList("day1b.txt")

    val compoundMeasurements = (0 until measurements.size - 2)
        .map { i -> measurements[i] + measurements[i + 1] + measurements[i + 2] }

    println(countArrayElementIncreases(compoundMeasurements))
}

fun day1a() {
    val measurements = readResourcesToIntList("day1a.txt")

    println(countArrayElementIncreases(measurements))
}


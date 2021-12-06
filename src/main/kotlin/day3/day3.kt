package day3

import java.io.File

fun main() {
    day3a()

    day3b()
}

fun day3a() {
    val readings = File(ClassLoader.getSystemResource("day3a.txt").toURI()).readLines()

    val gamma = calculateGamma(readings)
    val epsilon = calculateEpsilon(readings)

    val product = gamma.toUInt(2) * epsilon.toUInt(2)

    println(product)
}

fun day3b() {
    val readings = File(ClassLoader.getSystemResource("day3b.txt").toURI()).readLines()

    val oxygenGeneratorRating = calculateOxygenGeneratorRating(readings)
    val co2ScrubberRating = calculateCO2ScrubberRating(readings)

    println(oxygenGeneratorRating.toUInt(2) * co2ScrubberRating.toUInt(2))
}

fun calculateOxygenGeneratorRating(readings: List<String>): String {
    var filteredReadings = readings
    var index = 0
    while(filteredReadings.size > 1 && index < readings.size) {
        filteredReadings = keepMostCommon(filteredReadings, index)
        index++
    }
    return filteredReadings[0]
}

fun calculateCO2ScrubberRating(readings: List<String>): String {
    var filteredReadings = readings
    var index = 0
    while(filteredReadings.size > 1 && index < readings.size) {
        filteredReadings = keepLeastCommon(filteredReadings, index)
        index++
    }
    return filteredReadings[0]
}

fun keepMostCommon(readings: List<String>, index: Int): List<String> {
    val sum = sumOfIndex(readings, index).toDouble()
    val keepDigit = if ( sum == readings.size/2.0 ) '1'
        else if ( sum > readings.size/2.0 ) '1'
        else '0'

    return readings.filter { reading -> reading[index] == keepDigit }
}

fun keepLeastCommon(readings: List<String>, index: Int): List<String> {
    val sum = sumOfIndex(readings, index).toDouble()
    val keepDigit = if ( sum == readings.size/2.0 ) '0'
    else if ( sum > readings.size/2.0 ) '0'
    else '1'

    return readings.filter { reading -> reading[index] == keepDigit }
}

fun calculateGamma(readings: List<String>): String {
    val readingCount = sumEachIndex(readings)

    return readingCount.map { i ->
        if (i > readings.size / 2) "1"
        else if (i < readings.size / 2) "0"
        else throw RuntimeException("No most common bit")
    }.reduce { acc, i -> "$acc$i" }
}

fun calculateEpsilon(readings: List<String>): String {
    val readingCount = sumEachIndex(readings)

    return readingCount.map { i ->
        if (i < readings.size / 2) "1"
        else if (i > readings.size / 2) "0"
        else throw RuntimeException("No most common bit")
    }.reduce { acc, i -> "$acc$i" }
}

fun sumEachIndex(readings: List<String>): IntArray {
    val readingCount = IntArray(readings[0].length)

    for (i in readingCount.indices) {
        readingCount[i] = sumOfIndex(readings, i)
    }

    return readingCount
}

fun sumOfIndex(readings: List<String>, index: Int): Int {
    var sum = 0;
    for (reading in readings) {
        sum += reading[index].digitToInt()
    }
    return sum
}

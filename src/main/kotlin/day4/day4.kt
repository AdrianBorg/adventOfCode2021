package day4

import java.io.File

fun main() {
    val readings = File(ClassLoader.getSystemResource("day4a.txt").toURI()).readLines()

    val cards = processBingoFileReadings(readings)

    println("1")

}

fun processBingoFileReadings(readings: List<String>): List<BingoCard> {
    val inputs = readings[0].split(",").map(String::toInt)
    val cards: MutableList<BingoCard> = ArrayList()

    for (i in 2 until readings.size step 6) {
        cards.add(BingoCard(readings.subList(i, i+5)))
    }

    return cards
}

class BingoCard() {
    var rows: MutableList<List<Int>> = ArrayList()

    constructor(cardInput: List<String>) : this() {
        for (i in cardInput.indices) {
            val numbers = cardInput[i]
                .split(" ")
                .map(String::trim)
                .filter { item -> item != "" }
                .map(String::toInt)
            rows.add(i, numbers)
        }
    }

    fun contains(integers: List<Int>): Boolean {
        val match: Boolean = rows.none(integers::containsAll)

        
    }
}

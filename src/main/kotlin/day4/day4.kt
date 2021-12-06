package day4

import java.io.File

fun main() {
    day4a()

    day4b()
}

fun day4b() {
    val readings = File(ClassLoader.getSystemResource("day4a.txt").toURI()).readLines()

    val (cards, inputs) = processBingoFileReadings(readings)

    val (losingCard, index) = findLosingCardAndIndex(cards, inputs)

    val cardWithRemovedValues = getCardWithRemovedValues(losingCard, inputs.subList(0, index + 1))

    val product = sumOfValuesInCollection(cardWithRemovedValues) * inputs[index]

    println(product)
}

fun day4a() {
    val readings = File(ClassLoader.getSystemResource("day4b.txt").toURI()).readLines()

    val (cards, inputs) = processBingoFileReadings(readings)

    val (winningCard, index) = findFirstWinningCardAndIndex(cards, inputs)

    val cardWithRemovedValues = getCardWithRemovedValues(winningCard, inputs.subList(0, index + 1))

    val product = sumOfValuesInCollection(cardWithRemovedValues) * inputs[index]

    println(product)
}

fun getCardWithRemovedValues(winningCard: BingoCard, inputs: List<Int>): Set<Int> {
    return winningCard.asMutableSet().subtract(inputs.toSet())
}

fun findLosingCardAndIndex(cards: List<BingoCard>, inputs: List<Int>): Pair<BingoCard, Int> {
    var counter = inputs.size
    var filteredCards = cards

    while (filteredCards.size == 100) {
        counter--
        val subList = inputs.subList(0, counter)
        filteredCards = filteredCards.filter { card ->
            card.containsLine(subList)
        }
    }

    return Pair(cards.subtract(filteredCards.toSet()).first(), counter)
}

fun findFirstWinningCardAndIndex(cards: List<BingoCard>, inputs: List<Int>): Pair<BingoCard, Int> {
    var counter = 5
    var filteredCards: List<BingoCard> = listOf()

    while (filteredCards.isEmpty() && counter < inputs.size) {
        val sublist = inputs.subList(0, counter)
        filteredCards = cards.filter { card -> card.containsLine(sublist) }
        counter++
    }

    return Pair(filteredCards[0], counter - 2)
}

fun processBingoFileReadings(readings: List<String>): Pair<List<BingoCard>, List<Int>> {
    val inputs = readings[0].split(",").map(String::toInt)
    val cards: MutableList<BingoCard> = ArrayList()

    for (i in 2 until readings.size step 6) {
        cards.add(BingoCard(readings.subList(i, i + 5)))
    }

    return Pair(cards, inputs)
}

fun sumOfValuesInCollection(collection: Collection<Int>): Int {
    return collection.reduce { acc, i -> acc + i }
}

class BingoCard() {
    private var rows: MutableList<List<Int>> = ArrayList()

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

    fun containsLine(integers: List<Int>): Boolean {
        val rowsMatch = rows.any(integers::containsAll)
        val columnsMatch = rowToColumns().any(integers::containsAll)

        return rowsMatch || columnsMatch
    }

    fun asMutableSet(): MutableSet<Int> {
        val set = HashSet<Int>();
        rows.forEach(set::addAll)
        return set
    }

    private fun rowToColumns(): ArrayList<MutableList<Int>> {
        val columns = ArrayList<MutableList<Int>>()

        for (i in 0 until rows[0].size) {
            columns.add(ArrayList())
            for (row in rows) {
                columns[i].add(row[i])
            }
        }

        return columns
    }
}

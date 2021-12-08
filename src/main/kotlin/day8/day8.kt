package day8

import util.loadLinesFromResources

fun main() {
    day8a()

    day8b()
}

fun day8a() {

    // read inputs
    val inputs = loadLinesFromResources("day8.txt", ::split7SegmentInput)

    // split into signal pattern & output value
    val outputs = inputs.map { input -> input.takeLast(4) }

    // decypher digits
    val count = countEasyDigits(outputs)

    println(count)
}

fun day8b() {

    // read inputs
    val inputs = loadLinesFromResources("day8.txt", ::split7SegmentInput)

    var total = 0
    inputs.forEach { input ->
        val cypher = decipherLine(input)
        val value = translateLastFourToInt(input, cypher)
        total += value
    }

    println(total)
}

fun split7SegmentInput(input: String): MutableList<String> {
    return input.split(" | ")
        .map { list -> list.split(" ") }
        .map(List<String>::toMutableList)
        .reduce { acc, list ->
            acc.addAll(list)
            return acc
        }
}

fun countEasyDigits(input: List<List<String>>): Int {
    return input.map(::decipherLineAndCountEasyDigits)
        .map(Pair<Any?, Int>::second)
        .reduce { acc, i -> acc + i }
}

fun decipherLineAndCountEasyDigits(input: List<String>): Pair<Map<Int, Set<Char>>, Int> {
    val cypher = HashMap<Int, Set<Char>>()
    var counter = input.size

    for (word in input) {
        when (word.length) {
            2 -> cypher[1] = word.toSet()
            3 -> cypher[7] = word.toSet()
            4 -> cypher[4] = word.toSet()
            7 -> cypher[8] = word.toSet()
            else -> counter--
        }
    }
    return Pair(cypher, counter)
}

fun decipherLine(line: List<String>): Map<Set<Char>, Int> {
    val cypher = decipherLineAndCountEasyDigits(line).first.toMutableMap()

    val midLeftCorner = cypher[4]!!.subtract(cypher[7]!!)
    val bottomLeftCorner = cypher[8]!!.subtract(cypher[7]!!).subtract(cypher[4]!!)

    line.filter { code -> cypher.none { (_, s) -> s == code.toSet() } }
        .forEach { code ->
            when (code.length) {
                5 -> handleFiveCharCode(cypher, code.toSet(), midLeftCorner, bottomLeftCorner)
                6 -> handleSixCharCode(cypher, code.toSet(), midLeftCorner, bottomLeftCorner)
            }
        }

    return cypher.entries.associateBy({ it.value }) { it.key }
}

fun translateLastFourToInt(line: List<String>, cypher: Map<Set<Char>, Int>): Int {
    return line.takeLast(4)
        .map { i -> cypher[i.toSet()]!! }
        .map(Int::toString)
        .reduce { acc, i -> "$acc$i" }
        .toInt()
}

fun handleFiveCharCode(
    cypher: MutableMap<Int, Set<Char>>,
    code: Set<Char>,
    midLeftCorner: Set<Char>,
    bottomLeftCorner: Set<Char>
) {
    if (code.containsAll(bottomLeftCorner)) {
        cypher[2] = code
    } else if (code.containsAll(midLeftCorner)) {
        cypher[5] = code
    } else {
        cypher[3] = code
    }
}

fun handleSixCharCode(
    cypher: MutableMap<Int, Set<Char>>,
    code: Set<Char>,
    midLeftCorner: Set<Char>,
    bottomLeftCorner: Set<Char>
) {
    if (code.containsAll(bottomLeftCorner) && code.containsAll(midLeftCorner)) {
        cypher[6] = code
    } else if (code.containsAll(midLeftCorner)) {
        cypher[9] = code
    } else {
        cypher[0] = code
    }
}

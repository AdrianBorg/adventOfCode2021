package day7

import util.loadLineFromResourcesToIntList
import kotlin.math.abs

fun main() {
    day7a()

    day7b()
}
var fuelFunction = ::calculateFuelToGetToPosition

fun day7a() {
    fuelFunction = ::calculateFuelToGetToPosition

    val inputPositions = loadLineFromResourcesToIntList("day7.txt", ",")

    val pos = findOptimalFuel(inputPositions)

    val fuel = fuelFunction(inputPositions, pos)

    println(fuel)
}

fun day7b() {
    fuelFunction = ::calculateExtraFuelToGetToPosition

    val inputPositions = loadLineFromResourcesToIntList("day7.txt", ",")

    val pos = findOptimalFuel(inputPositions)

    val fuel = fuelFunction(inputPositions, pos)

    println(fuel)
}

fun findOptimalFuel(list: List<Int>): Int {
    var right = list.maxOrNull()!!
    var left = list.minOrNull()!!
    var pos = (right - left) / 2

    do {
        val dir = gradientDirection(list, pos)

        if (dir < 0) {
            right = pos
            pos = (right + left) / 2
        } else if (dir > 0) {
            left = pos
            pos = (right + left) / 2
        }
    } while (dir != 0)

    return pos
}

fun gradientDirection(list: List<Int>, pos: Int): Int {
    val fuelMid = fuelFunction(list, pos)
    val fuelLeftPoint = fuelFunction(list, pos - 1)
    val fuelRightPoint = fuelFunction(list, pos + 1)

    return when (minOf(fuelMid, fuelLeftPoint, fuelRightPoint)) {
        fuelMid -> 0
        fuelRightPoint -> 1
        fuelLeftPoint -> -1
        else -> throw RuntimeException()
    }
}

fun calculateFuelToGetToPosition(crabSubs: List<Int>, pos: Int): Long {
    return crabSubs.map { abs(it - pos) }
        .map(Int::toLong)
        .reduce { acc, i -> acc + i }
}

fun calculateExtraFuelToGetToPosition(crabSubs: List<Int>, pos: Int): Long {
    return crabSubs.map { abs(it - pos) }
        .map(Int::toLong)
        .map(::calculateTriangleNumber)
        .reduce { acc, i -> acc +i }
}

fun calculateTriangleNumber(n: Long): Long {
    return (n * (n + 1)) / 2
}

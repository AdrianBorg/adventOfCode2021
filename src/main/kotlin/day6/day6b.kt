package day6

import util.loadLineFromResourcesToIntList
import util.toItemCounterMap

fun main() {
    day6b()
}

const val firstSpawnTimer = 8
const val respawnTimer = 6
const val totalDaysToSimulate = 256

fun day6b() {
    val inputTimers = loadLineFromResourcesToIntList("day6.txt", ",")

    val counterMap = inputTimers.toItemCounterMap()

    for (i in 0 until totalDaysToSimulate) {
        simulateDay(counterMap)
    }

    val totalFish = counterMap.entries.map { mutableEntry -> mutableEntry.value }
        .reduce { acc, i -> acc + i }

    println(totalFish)
}

fun simulateDay(lanternFish: MutableMap<Int, Long>) {
    val spawningFish = lanternFish.getOrDefault(0, 0)
    for (i in 1..firstSpawnTimer) {
        lanternFish[i - 1] = lanternFish.getOrDefault(i, 0)
    }
    lanternFish[respawnTimer] = lanternFish.getOrDefault(respawnTimer, 0) + spawningFish
    lanternFish[firstSpawnTimer] = spawningFish
}

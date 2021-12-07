package day6

import util.loadLineFromResourcesToIntList

fun main() {
    day6a()
}

fun day6a() {

    val inputLanternFish = loadLineFromResourcesToIntList("day6.txt", ",")
        .map(::LanternFish)

    val lanternFish = inputLanternFish.toMutableList()

    for (day in 0 until 80) {
        val newFishes: MutableList<LanternFish> = ArrayList()
        for (fish in lanternFish) {
            val newFish = fish.simulateDay()
            if (newFish != null) {
                newFishes.add(newFish)
            }
        }
        lanternFish.addAll(newFishes)
    }

    println(lanternFish.size)

}

class LanternFish(var spawnTimer: Int) {
    private val initialTimer = 8
    private val respawnTimer = 6

    fun simulateDay(): LanternFish? {
        spawnTimer--
        if (spawnTimer < 0) {
            spawnTimer = respawnTimer
            return spawnFish()
        }
        return null
    }

    private fun spawnFish(): LanternFish {
        this.spawnTimer = respawnTimer
        return LanternFish(initialTimer)
    }
}

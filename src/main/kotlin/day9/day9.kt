package day9

import util.loadLinesFromResources

fun main() {
    day9a()

    day9b()
}

fun day9b() {
    val heightMap = loadLinesFromResources("day9.txt", ::loadHeightMapLine)

    val minPoints = compareAdjacentSpots(heightMap) { a, b -> a < b }

    val checkedLocations = HashSet<Pair<Int, Int>>()

    val basinSizes = minPoints.map {
            minPoint -> countBasinSize(heightMap, minPoint, checkedLocations)
    }

    println(basinSizes.sorted().takeLast(3).reduce { acc, i -> acc * i })
}

fun day9a() {
    val heightMap = loadLinesFromResources("day9.txt", ::loadHeightMapLine)

    val minPoints = compareAdjacentSpots(heightMap) { a, b -> a < b }

    val solution = minPoints
        .map { pair -> heightMap[pair.first][pair.second] }
        .reduce { acc, i -> acc + i } + minPoints.size

    println(solution)
}

fun loadHeightMapLine(line: String): List<Int> {
    return line.toList().map(Char::digitToInt)
}

fun compareAdjacentSpots(
    heightMap: List<List<Int>>,
    function: (Int, Int) -> Boolean
): List<Pair<Int, Int>> {
    val xBounds = heightMap[0].indices
    val yBounds = heightMap.indices
    val result = ArrayList<Pair<Int, Int>>()

    for (y in yBounds) {
        for (x in xBounds) {
            val adjacentSpots = findAdjacentIndices(heightMap, Pair(y, x))

            val fitsCriteria = adjacentSpots.all { adjacentSpot ->
                function(heightMap[y][x], heightMap[adjacentSpot.first][adjacentSpot.second])
            }

            if (fitsCriteria) {
                result.add(Pair(y, x))
            }
        }
    }

    return result
}

fun countBasinSize(
    heightMap: List<List<Int>>,
    location: Pair<Int, Int>,
    checkedLocations: MutableSet<Pair<Int, Int>>
): Int {

    if ( !checkedLocations.add(location) ) { return 0 }

    val adjacentBasinSpots = findAdjacentIndices(heightMap, location)
        .filter { heightMap[it.first][it.second] != 9 }

    val sizeFromAdjacent = adjacentBasinSpots
        .map { nextLoc -> countBasinSize(heightMap, nextLoc, checkedLocations) }
        .reduceOrNull { acc, i -> acc + i } ?: 0

    return 1 + sizeFromAdjacent
}

fun findAdjacentIndices(
    heightMap: List<List<Int>>,
    loc: Pair<Int, Int>
): List<Pair<Int, Int>> {
    val adjacentIndices = ArrayList<Pair<Int, Int>>()

    val xBounds = heightMap[0].indices
    val yBounds = heightMap.indices

    if (yBounds.contains(loc.first - 1)) {
        adjacentIndices.add(Pair(loc.first - 1, loc.second))
    }
    //check below
    if (yBounds.contains(loc.first + 1)) {
        adjacentIndices.add(Pair(loc.first + 1, loc.second))
    }
    //check left
    if (xBounds.contains(loc.second - 1)) {
        adjacentIndices.add(Pair(loc.first, loc.second - 1))
    }
    //check right
    if (xBounds.contains(loc.second + 1)) {
        adjacentIndices.add(Pair(loc.first, loc.second + 1))
    }

    return adjacentIndices
}


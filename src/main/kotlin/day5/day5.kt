package day5

import util.getOrDefault
import util.stepFromTo
import kotlin.math.max

fun main() {
    day5a()

    day5b()
}

fun day5b() {
    val lines = util.loadLinesFromResources("day5.txt", ::createLine)
    val tally = tallyPoints(lines)
    val solution = tally.filter { entry -> entry.value > 1 }.size

    println(solution)
}

fun day5a() {

    val lines = util.loadLinesFromResources("day5.txt", ::createLine)
    val straightLines = lines.filter(::isStraightLine)
    val tally = tallyPoints(straightLines)
    val solution = tally.filter { entry -> entry.value > 1 }.size

    println(solution)

}

data class Point(val x: Int, val y: Int)

data class Line(val point1: Point, val point2: Point) {

    fun getPoints(): List<Point> {
        val yProgression = stepFromTo(point1.y, point2.y).toCollection(ArrayList())
        val xProgression = stepFromTo(point1.x, point2.x).toCollection(ArrayList())

        val steps = max(yProgression.size, xProgression.size)

        return (0 until steps).map { index ->
            Point(
                xProgression.getOrDefault(index, xProgression[0]),
                yProgression.getOrDefault(index, yProgression[0])
        ) }
    }

    fun isHorizontal(): Boolean {
        return point1.x == point2.x
    }

    fun isVertical(): Boolean {
        return point1.y == point2.y
    }
}

fun createLine(line: String): Line {
    val pointStrings = line.split(" -> ")
        .map { pointString -> pointString.split(",").map(String::toInt) }
    return Line(
        Point(pointStrings[0][0], pointStrings[0][1]),
        Point(pointStrings[1][0], pointStrings[1][1])
    )
}

fun isStraightLine(line: Line): Boolean {
    return line.isVertical() || line.isHorizontal()
}

fun tallyPoints(lines: List<Line>): HashMap<Point, Int> {
    val tally: HashMap<Point, Int> = HashMap()

    for (line in lines) {
        line.getPoints().forEach { point ->
            tally[point] = tally.getOrDefault(point, 0) + 1
        }
    }

    return tally
}

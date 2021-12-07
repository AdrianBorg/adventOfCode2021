package util

import java.io.File

fun <T> loadLinesFromResources(
    filename: String,
    func: (line: String) -> T
): List<T> {
    return File(ClassLoader.getSystemResource(filename).toURI()).readLines()
        .map { line -> func(line) }
}

fun readResourcesToIntList(filename: String): List<Int> {
    return loadLinesFromResources(filename, String::toInt)
}

fun loadLineFromResourcesToIntList(filename: String, separator: String): List<Int> {
    return File(ClassLoader.getSystemResource(filename).toURI()).readLines()[0]
        .split(separator)
        .map(String::toInt)
}

fun sumOfValuesInCollection(collection: Collection<Int>): Int {
    return collection.reduce { acc, i -> acc + i }
}

fun stepFromTo(from: Int, to: Int): IntProgression {
    val step = if (from > to) -1 else 1
    return IntProgression.fromClosedRange(from, to, step)
}

fun <T> List<T>.getOrDefault(index: Int, default: T): T {
    return if (index in 0..lastIndex) get(index) else default
}

fun <T> List<T>.toItemCounterMap(): MutableMap<T, Long> {
    val counter = HashMap<T, Long>()
    this.forEach { item ->
        counter[item] = counter.getOrDefault(item, 0) + 1
    }
    return counter
}

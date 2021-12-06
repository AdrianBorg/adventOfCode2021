package util

import java.io.File

class util {
}

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

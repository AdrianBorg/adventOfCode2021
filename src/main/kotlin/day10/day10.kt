package day10

import util.loadLinesFromResources

fun main() {
    day10a()

    day10b()
}

val matchingBrace: Map<Char, Char> = mapOf(Pair('(', ')'), Pair('[', ']'), Pair('{', '}'), Pair('<', '>'))

val mistakeScore: Map<Char, Int> = mapOf(Pair(')', 3), Pair(']', 57), Pair('}', 1197), Pair('>', 25137))

val autoCompleteScore: Map<Char, Int> = mapOf(Pair(')', 1), Pair(']', 2), Pair('}', 3), Pair('>', 4))

const val specialChar = ' '

fun day10a() {
    val lines = loadLinesFromResources("day10.txt")

    val firstIncorrectValues = lines.mapNotNull(::findIncorrectClosingChar)
        .filter { it != specialChar }
    
    val solution = firstIncorrectValues.map(mistakeScore::get)
        .reduce { acc, i -> acc!! + i!! }
    
    println(solution)
}

fun day10b() {
    val lines = loadLinesFromResources("day10.txt")

    val incompleteLines = lines.filter { findIncorrectClosingChar(it) == specialChar }

    val autoCompleteChars = incompleteLines.map(::findRemainingAutocompleteChars)
        .map { s -> s.map { matchingBrace[it]!! } }

    val scores = autoCompleteChars.map(::calculateAutocompleteScore)

    val middleScore = scores.sorted()[scores.indices.last/2]

    println(middleScore)
}

fun calculateAutocompleteScore(chars: List<Char>): Long {
    return chars.map { autoCompleteScore[it]!! }
        .map(Int::toLong)
        .reduce { acc, i -> acc * 5 + i }
}

fun findRemainingAutocompleteChars(line: String): String {
    val stack = ArrayDeque<Char>()

    for (char in line) {
        if (char in matchingBrace.keys) {
            stack.addFirst(char)
        } else {
            stack.removeFirst()
        }
    }

    return stack.map(Char::toString).reduce { acc, c -> acc + c }
}

fun findIncorrectClosingChar(line: String): Char? {
    val stack = ArrayDeque<Char>()

    for (char in line) {
        if (char in matchingBrace.keys) {
            stack.addFirst(char)
        } else {
            val matchingChar = stack.removeFirst()
            if (char != matchingBrace[matchingChar]) {
                return char
            }
        }
    }

    return if (stack.size == 0) null else specialChar
}

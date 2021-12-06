package day2

fun main() {

    day2a()

    day2b()

}

fun day2a() {

    val instructions = readInstructionsFileFromResourcesToList("day2a.txt")

    println(calculateSimpleDistance(instructions))

}

fun day2b() {

    val instructions = readInstructionsFileFromResourcesToList("day2b.txt")

    println(calculateComplexDistance(instructions))

}

fun calculateComplexDistance(instructions: List<SubmarineInstruction>): Int {
    var horizontal = 0
    var depth = 0
    var aim = 0

    instructions.forEach { instruction ->
        if (instruction.command == Instruction.FORWARD) {
            horizontal += instruction.distance
            depth += aim * instruction.distance
        } else {
            val multiplier = (if (instruction.command == Instruction.DOWN) 1 else -1)
            aim += multiplier * instruction.distance
        }
    }

    return horizontal * depth
}

fun calculateSimpleDistance(instructions: List<SubmarineInstruction>): Int {
    var horizontal = 0
    var depth = 0

    instructions.forEach { instruction ->
        if (instruction.command == Instruction.FORWARD) {
            horizontal += instruction.distance
        } else {
            val multiplier = (if (instruction.command == Instruction.DOWN) 1 else -1)
            depth += multiplier * instruction.distance
        }
    }

    return horizontal * depth
}

class SubmarineInstruction(val command: Instruction, val distance: Int)

enum class Instruction {
    FORWARD, DOWN, UP;
}

fun readInstructionsFileFromResourcesToList(filename: String): List<SubmarineInstruction> {
    return util.loadLinesFromResources(filename, ::decodeInstruction)
}

fun decodeInstruction(line: String): SubmarineInstruction {
    val split = line.split(" ")
    val instruction = Instruction.valueOf(split[0].uppercase())
    val distance = split[1].toInt()

    return SubmarineInstruction(instruction, distance)
}

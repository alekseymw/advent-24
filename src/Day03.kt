const val MUL_REGEX_STRING = "mul\\(\\d{1,3},\\d{1,3}\\)"
const val DO_DO_NOT_STRING = "don't\\(\\)|do\\(\\)"

fun main() {

    fun part1(input: List<String>): Int {
        return input.sumOf { inputString ->
            MUL_REGEX_STRING.toRegex().findAll(inputString)
                .map { it.value }
                .map { str -> str.substring(str.indexOf('(') + 1, str.indexOf(')')).split(',').map { it.toInt() } }
                .sumOf { it.first() * it.last() }
        }
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        var enableSum = true

        input.forEach { inputString ->
            "$MUL_REGEX_STRING|$DO_DO_NOT_STRING".toRegex().findAll(inputString)
                .map { it.value }
                .forEach { instruction ->
                    when (instruction) {
                        "do()" -> enableSum = true
                        "don't()" -> enableSum = false
                        else -> {
                            if (enableSum) {
                                val numbers =
                                    instruction.substring(instruction.indexOf('(') + 1, instruction.indexOf(')'))
                                        .split(',')
                                        .map { it.toInt() }
                                sum += if (numbers.size == 2) numbers.first() * numbers.last() else 0
                            }
                        }
                    }
                }
        }

        return sum
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 161)
    check(part2(testInput) == 48)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
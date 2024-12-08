fun main() {

    val concatenation = { first: Long, second: Long -> "$first$second".toLong() }
    val operations = listOf(
        { first: Long, second: Long -> first + second },
        { first: Long, second: Long -> first * second }
    )

    fun existsSolution(
        testValue: Long,
        acc: Long,
        numbers: List<Long>,
        operations: List<(Long, Long) -> Long>
    ): Boolean {
        if (numbers.isEmpty()) {
            return testValue == acc
        }
        return operations.any { existsSolution(testValue, it.invoke(acc, numbers[0]), numbers.drop(1), operations) }
    }

    fun parseString(inputString: String): Pair<Long, List<Long>> {
        val (testValueString, numberString) = inputString.split(": ")
        return testValueString.toLong() to numberString.split(" ").map { it.toLong() }.toList()
    }

    fun part1(input: List<String>): Long {
        return input.map { parseString(it) }
            .sumOf { (testValue, numbers) ->
                if (existsSolution(testValue, numbers[0], numbers.drop(1), operations)) {
                    testValue
                } else 0
            }
    }

    fun part2(input: List<String>): Long {
        return input.map { parseString(it) }
            .sumOf { (testValue, numbers) ->
                if (existsSolution(testValue, numbers[0], numbers.drop(1), operations + concatenation)) {
                    testValue
                } else 0
            }
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 3749L)
    check(part2(testInput) == 11387L)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
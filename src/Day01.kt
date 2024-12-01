import kotlin.math.absoluteValue

fun main() {

    fun parseInput(input: List<String>) = input.flatMap { string ->
        val (first, second) = string.split("\\s+".toRegex())
        listOf(1 to first.toInt(), 2 to second.toInt())
    }.sortedWith(compareBy<Pair<Int, Int>> { it.first }.thenBy { it.second })

    fun part1(input: List<String>): Int {
        val list = parseInput(input)
        return input.indices.sumOf { (list[it].second - list[input.size + it].second).absoluteValue }
    }

    fun part2(input: List<String>): Int {
        val list = parseInput(input)
        return input.indices.sumOf { index ->
            list[index].second * list.count { it.first == 2 && it.second == list[index].second }
        }
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

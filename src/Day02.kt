import kotlin.math.absoluteValue

enum class Direction { ASC, DESC, UNKNOWN }

fun main() {

    fun parseIntoIntList(string: String) = string.split("\\s+".toRegex()).map { it.toInt() }

    fun isAllowedDifference(diff: Int) = diff.absoluteValue in 1..3

    fun isInTheSameDirection(diff: Int, direction: Direction) =
        (diff > 0 && direction == Direction.ASC) || (diff < 0 && direction == Direction.DESC)

    fun isLevelSafe(levels: List<Int>): Boolean {
        var direction = Direction.UNKNOWN
        var isSafe = true
        for (i in 1..<levels.size) {
            val diff = levels[i] - levels[i - 1]
            if (!isAllowedDifference(diff)) {
                isSafe = false
                break
            }

            if (direction == Direction.UNKNOWN) {
                direction = if (diff > 0) Direction.ASC else Direction.DESC
            } else {
                if (!isInTheSameDirection(diff, direction)) {
                    isSafe = false
                    break
                }
            }
        }

        return isSafe
    }

    fun part1(input: List<String>): Int {
        return input.count { string ->
            val levels = parseIntoIntList(string)
            isLevelSafe(levels)
        }
    }

    fun part2(input: List<String>): Int {
        return input.count { string ->
            val levels = parseIntoIntList(string)

            isLevelSafe(levels) || levels.indices.any {
                val copy = levels.toMutableList()
                copy.removeAt(it)
                isLevelSafe(copy)
            }
        }
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
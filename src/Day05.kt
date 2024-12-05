fun main() {

    fun parseRules(input: List<String>) = input.takeWhile { it.isNotBlank() }
        .map { it.split('|') }
        .map { it[0] to it[1] }

    fun fixedUpdate(update: List<String>, rules: List<Pair<String, String>>): List<String> {
        val result = update.toMutableList()

        do {
            var changed = false
            result.forEachIndexed { index, value ->
                if (index > 0 && rules.contains(value to result[index - 1])) {
                    changed = true
                    val temp = result[index]
                    result[index] = result[index - 1]
                    result[index - 1] = temp
                }
            }
        } while (changed)

        return result
    }

    fun isPlacedIncorrectly(
        currentPageNumber: String,
        previousPageNumber: String,
        rules: List<Pair<String, String>>
    ) = rules.contains(currentPageNumber to previousPageNumber)

    fun part1(input: List<String>): Int {
        val rules = parseRules(input)

        return input.drop(rules.size + 1)
            .map { it.split(",") }
            .filter { it.filterIndexed { index, currentPageNumber -> index != 0 && isPlacedIncorrectly(currentPageNumber, it[index - 1], rules) }.isEmpty() }
            .sumOf { it[it.size / 2].toInt() }
    }

    fun part2(input: List<String>): Int {
        val rules = parseRules(input)
        return input.asSequence().drop(rules.size + 1)
            .map { it.split(',') }
            .filter { it.filterIndexed { index, currentPageNumber -> index != 0 && isPlacedIncorrectly(currentPageNumber, it[index - 1], rules) }.isNotEmpty() }
            .map { fixedUpdate(it, rules) }
            .sumOf { it[it.size / 2].toInt() }
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
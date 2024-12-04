private data class Movement(val i: Int, val j: Int)

fun main() {

    val allDirections = listOf(
        Movement(-1, 0),
        Movement(-1, 1),
        Movement(0, 1),
        Movement(1, 1),
        Movement(1, 0),
        Movement(1, -1),
        Movement(0, -1),
        Movement(-1, -1),
    )

    val leftDiagonal = listOf(Movement(-1, -1), Movement(1, 1))
    val rightDiagonal = listOf(Movement(-1, 1), Movement(1, -1))

    fun isValid(i: Int, j: Int, maxI: Int, maxJ: Int): Boolean {
        return i in 0..<maxI && j in 0..<maxJ
    }

    fun searchWord(startI: Int, startJ: Int, maxI: Int, maxJ: Int, input: List<String>): Int {
        if (input[startI][startJ] != 'X') return 0

        return allDirections.count { (dx, dy) ->
            generateSequence(Pair(startI, startJ)) { (i, j) ->
                val newI = i + dx
                val newJ = j + dy
                if (isValid(newI, newJ, maxI, maxJ)) Pair(newI, newJ) else Pair(startI, startJ)
            }
                .take(4)
                .toList()
                .zip(listOf('X', 'M', 'A', 'S'))
                .all { input[it.first.first][it.first.second] == it.second }
        }
    }

    fun searchCross(startI: Int, startJ: Int, maxI: Int, maxJ: Int, input: List<String>): Int {
        if (input[startI][startJ] != 'A') return 0

        val diagonalsFound = listOf(leftDiagonal, rightDiagonal).count { diagonals ->
            val availableChars = mutableSetOf('M', 'S')
            diagonals.forEach { (dx, dy) ->
                val x = startI + dx
                val y = startJ + dy
                if (!isValid(x, y, maxI, maxJ) || input[x][y] !in availableChars) return@forEach
                availableChars.remove(input[x][y])
            }
            availableChars.isEmpty()
        }
        return if (diagonalsFound == 2) 1 else 0
    }

    fun part1(input: List<String>): Int {
        return input.mapIndexed { row, str ->
            str.withIndex().sumOf { (column, _) -> searchWord(row, column, input.size, str.length, input) }
        }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.mapIndexed { row, str ->
            str.withIndex().sumOf { (column, _) -> searchCross(row, column, input.size, str.length, input) }
        }.sum()
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
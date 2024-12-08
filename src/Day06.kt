private data class Guard(var x: Int, var y: Int, var moveDirection: MoveDirection)

private enum class MoveDirection(val dx: Int, val dy: Int) {
    UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0);

    companion object {
        fun fromChar(c: Char): MoveDirection {
            return when (c) {
                '<' -> LEFT
                '>' -> RIGHT
                '^' -> UP
                else -> DOWN
            }
        }

        fun rotate(currentDirection: MoveDirection): MoveDirection {
            return when (currentDirection) {
                UP -> RIGHT
                RIGHT -> DOWN
                DOWN -> LEFT
                else -> UP
            }
        }
    }
}

fun main() {

    fun isWithinField(x: Int, y: Int, input: List<String>): Boolean {
        return y in input.indices && x in input[0].indices
    }

    fun traverseField(input: List<String>, guard: Guard, visited: MutableList<MutableList<Boolean>>) {
        if (!isWithinField(guard.x, guard.y, input)) return

        visited[guard.y][guard.x] = true

        var newX = guard.x + guard.moveDirection.dx
        var newY = guard.y + guard.moveDirection.dy

        if (!isWithinField(newX, newY, input)) {
            return
        }

        if (input[newY][newX] == '#') {
            while (input[newY][newX] == '#') {
                guard.moveDirection = MoveDirection.rotate(guard.moveDirection)
                newX = guard.x + guard.moveDirection.dx
                newY = guard.y + guard.moveDirection.dy
            }
        }

        guard.x = newX
        guard.y = newY
        traverseField(input, guard, visited)
    }

    fun part1(input: List<String>): Int {

        var guard = Guard(-1, -1, MoveDirection.UP)

        for (i in input.indices) {
            val char = setOf('<', '>', '^', 'v').find { input[i].contains(it) }
            if (char != null) {
                guard = Guard(input[i].indexOf(char), i, MoveDirection.fromChar(char))
                break
            }
        }

        val visited = MutableList(input.size) { MutableList(input[0].length) { false } }

        traverseField(input, guard, visited)

        return visited.sumOf { row -> row.count { it } }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 41)
//    check(part2(testInput) == 123)

    val input = readInput("Day06")
    part1(input).println()
//    part2(input).println()
}
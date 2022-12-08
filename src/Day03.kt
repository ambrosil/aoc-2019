import kotlin.math.abs

fun main() {

    data class Point(val x: Int, val y: Int)
    data class Info(var visitedBy: Set<Int> = mutableSetOf(), var steps1: Int = 0, var steps2: Int = 0)

    class Space {
        val points = mutableMapOf<Point, Info>()
        var currPos = Point(0, 0)
        var steps = 0

        fun visit(p: Point, wire: Int, steps: Int) {
            val info = points.getOrDefault(p, Info())

            if (wire == 1 && info.steps1 == 0) {
                info.steps1 = steps
            } else if (wire == 2 && info.steps2 == 0) {
                info.steps2 = steps
            }

            info.visitedBy += wire
            points[p] = info
        }

        fun resetPosition() {
            currPos = Point(0, 0)
            steps = 0
        }

        fun process(cmd: String, wireNum: Int) {
            val direction = cmd[0]
            val n = cmd.drop(1).toInt()

            when (direction) {
                'U' -> {
                   for (i in 0..n) {
                       visit(Point(currPos.x, currPos.y + i), wireNum, i + steps)
                   }
                    currPos = Point(currPos.x, currPos.y + n)
                }

                'D' -> {
                    for (i in 0..n) {
                        visit(Point(currPos.x, currPos.y - i), wireNum, i + steps)
                    }
                    currPos = Point(currPos.x, currPos.y - n)
                }

                'L' -> {
                    for (i in 0..n) {
                        visit(Point(currPos.x - i, currPos.y), wireNum, i + steps)
                    }
                    currPos = Point(currPos.x - n, currPos.y)
                }

                'R' -> {
                    for (i in 0..n) {
                        visit(Point(currPos.x + i, currPos.y), wireNum, i + steps)
                    }
                    currPos = Point(currPos.x + n, currPos.y)
                }

                else -> error("direction $direction not possible")
            }

            steps += n
        }

        fun minDistance(): Int {
            return points.entries.filter {
                it.value.visitedBy.size == 2 && it.key.x != 0 && it.key.y != 0
            }
            .minOf { abs(it.key.x) + abs(it.key.y) }
        }

        fun minSteps(): Int {
            return points.entries.filter {
                it.value.visitedBy.size == 2 && it.key.x != 0 && it.key.y != 0
            }
            .minOf { it.value.steps1 + it.value.steps2 }
        }
    }

    fun common(input: List<String>): Space {
        val (wire1, wire2) = input
        val space = Space()

        wire1.split(",").forEach { space.process(it, 1) }
        space.resetPosition()
        wire2.split(",").forEach { space.process(it, 2) }

        return space
    }

    fun part1(input: List<String>) = common(input).minDistance()
    fun part2(input: List<String>) = common(input).minSteps()

    val input = readInput("inputs/Day03")
    println(part1(input))
    println(part2(input))
}

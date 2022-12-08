import kotlin.math.floor

fun main() {

    fun part1(input: List<String>) = input.sumOf {
        (floor(it.toDouble() / 3) - 2).toInt()
    }

    fun part2(input: List<String>) = input.sumOf { it.requiredFuel() }

    val input = readInput("inputs/Day01")
    println(part1(input))
    println(part2(input))
}

fun String.requiredFuel() = calcFuel(toInt())

fun calcFuel(required: Int): Int {
    val value = (floor(required / 3.0) - 2).toInt()
    return if (value < 0) {
        0
    } else {
        value + calcFuel(value)
    }
}

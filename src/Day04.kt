fun main() {

    fun parse(input: List<String>, block: (n: Int) -> Boolean): Int {
        val (start, end) = input
            .single()
            .split("-")
            .map { it.toInt() }

        return (start..end).count { block(it) }
    }

    fun part1(input: List<String>) = parse(input) { it.meetsCriteria1() }
    fun part2(input: List<String>) = parse(input) { it.meetsCriteria2() }

    val input = readInput("inputs/Day04")
    println(part1(input))
    println(part2(input))
}

fun Int.meetsCriteria1(): Boolean {
    val windows = toString().toList().windowed(2)
    val existDouble = windows.any { it.size != it.toSet().size }
    val allIncreasing = windows.all { (one, two) -> one <= two }

    return existDouble && allIncreasing
}

fun Int.meetsCriteria2(): Boolean {
    val windows = toString().toList().windowed(2)
    val windows3 = toString().toList().windowed(3)

    val doubles = windows.filter { it.size != it.toSet().size }
    val triples = windows3.filter { (one, two, three) -> one == two && two == three }

    val strangeRule = doubles.any { d ->
        triples.all { t ->
            d.toSet().single() != t.toSet().single()
        }
    }

    val allIncreasing = windows.all { (one, two) -> one <= two }
    return strangeRule && allIncreasing
}

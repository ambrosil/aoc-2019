fun main() {

    fun MutableList<Int>.compute(a: Int, b: Int): Int {
        var idx = 0

        val program = this
        program[1] = a
        program[2] = b

        while (program[idx] != 99) {
            if (program[idx] == 1) {
                program[program[idx+3]] = program[program[idx+1]] + program[program[idx+2]]
            } else if (program[idx] == 2) {
                program[idx+3] = program[program[idx+1]] * program[program[idx+2]]
            }

            idx += 4
        }

        return program[0]
    }

    fun part1(input: List<String>): Int {
        val program = input.single()
            .split(",")
            .map { it.toInt() }
            .toMutableList()

        return program.compute(12, 2)
    }

    fun part2(input: List<String>): Int {
        val program = input.single()
            .split(",")
            .map { it.toInt() }
            .toMutableList()

        for (i in 0..99) {
            for (j in 0..99) {
                if (ArrayList(program).compute(i, j) == 19690720) {
                    return 100 * i + j
                }
            }
        }

        return 0
    }

    val input = readInput("inputs/Day02")
    println(part1(input))
    println(part2(input))
}

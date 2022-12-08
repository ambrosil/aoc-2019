import java.io.InputStream
import java.lang.System.`in`
import java.nio.charset.Charset

fun main() {

    fun InputStream.readInput(charset: Charset = Charsets.UTF_8): Int {
        print("Inserire input: ")
        return this.bufferedReader(charset).use { it.readLine().toInt() }
    }

    fun process(program: MutableList<Int>) {
        var idx = 0

        while (program[idx] != 99) {
            val instruction = program[idx].toString().padStart(4, '0')
            val opcode = instruction.takeLast(2)

            val firstMode = instruction.takeLast(3).first()
            val secondMode = instruction.takeLast(4).first()

            val n1 = when (firstMode) {
                '0' -> program[program[idx+1]]
                '1' -> program[idx+1]
                else -> 0
            }

            val n2 = try {
                when (secondMode) {
                    '0' -> program[program[idx+2]]
                    '1' -> program[idx+2]
                    else -> 0
                }
            } catch (e: Exception) {
                0
            }

            when (opcode) {
                "01" -> {
                    program[program[idx+3]] = n1 + n2
                    idx += 4
                }

                "02" -> {
                    program[program[idx+3]] = n1 * n2
                    idx += 4
                }

                "03" -> {
                    program[program[idx+1]] = `in`.readInput()
                    idx += 2
                }

                "04" -> {
                    println(n1)
                    idx += 2
                }

                "05" -> {
                    if (n1 != 0) {
                        idx = n2
                    } else {
                        idx += 3
                    }
                }

                "06" -> {
                    if (n1 == 0) {
                        idx = n2
                    } else {
                        idx += 3
                    }
                }

                "07" -> {
                    if (n1 < n2) {
                        program[program[idx+3]] = 1
                    } else {
                        program[program[idx+3]] = 0
                    }

                    idx += 4
                }

                "08" -> {
                    if (n1 == n2) {
                        program[program[idx+3]] = 1
                    } else {
                        program[program[idx+3]] = 0
                    }

                    idx += 4
                }
            }
        }
    }

    fun parse(input: List<String>): MutableList<Int> {
        return input.single()
            .split(",")
            .map { it.toInt() }
            .toMutableList()
    }

    fun part1(input: List<String>) {
        process(parse(input))
    }

    fun part2(input: List<String>) {
        process(parse(input))
    }

    val input = readInput("inputs/Day05")
    part1(input)
    part2(input)
}

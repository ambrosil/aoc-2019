import java.io.InputStream
import java.lang.System.`in`
import java.nio.charset.Charset

fun main() {

    fun InputStream.readInput(charset: Charset = Charsets.UTF_8): Long {
        print("Inserire input: ")
        return this.bufferedReader(charset).use { it.readLine().toLong() }
    }

    fun MutableMap<Long, Long>.value(modes: List<Char>, index: Int, relativeBase: Long, ip: Long): Long {
        return when (modes[index-1]) {
            '0' -> gets(gets(index+ip))
            '1' -> gets(index+ip)
            '2' -> gets(gets(index+ip) + relativeBase)
            else -> 0
        }
    }

    fun MutableMap<Long, Long>.address(modes: List<Char>, index: Int, relativeBase: Long, ip: Long): Long {
        return when (modes[index-1]) {
            '0' -> gets(index+ip)
            '1' -> error("wrong mode ${modes[index-1]}")
            '2' -> gets(index+ip) + relativeBase
            else -> 0
        }
    }

    fun process(program: MutableMap<Long, Long>) {
        var ip = 0L
        var relativeBase = 0L

        while (program[ip] != 99L) {
            val instruction = program[ip].toString().padStart(5, '0')
            val opcode = instruction.takeLast(2)

            val firstMode = instruction.takeLast(3).first()
            val secondMode = instruction.takeLast(4).first()
            val thirdMode = instruction.takeLast(5).first()
            val modes = listOf(firstMode, secondMode, thirdMode)

            val v1 = program.value(modes, 1, relativeBase, ip)
            val v2 = program.value(modes, 2, relativeBase, ip)
            val address = program.address(modes, 3, relativeBase, ip)

            when (opcode) {
                "01" -> {
                    program.sets(address, v1 + v2)
                    ip += 4
                }

                "02" -> {
                    program.sets(address, v1 * v2)
                    ip += 4
                }

                "03" -> {
                    val address = program.address(modes, 1, relativeBase, ip)
                    val v = `in`.readInput()
                    program.sets(address, v)
                    ip += 2
                }

                "04" -> {
                    println(v1)
                    ip += 2
                }

                "05" -> {
                    if (v1 != 0L) {
                        ip = v2
                    } else {
                        ip += 3
                    }
                }

                "06" -> {
                    if (v1 == 0L) {
                        ip = v2
                    } else {
                        ip += 3L
                    }
                }

                "07" -> {
                    val v = if (v1 < v2) 1L else 0L
                    program.sets(address, v)

                    ip += 4L
                }

                "08" -> {
                    val v = if (v1 == v2) 1L else 0L
                    program.sets(address,  v)

                    ip += 4L
                }

                "09" -> {
                    relativeBase += v1
                    ip += 2L
                }
            }
        }
    }

    fun parse(input: List<String>): MutableMap<Long, Long> {
        val map = mutableMapOf<Long, Long>()

        input.single()
            .split(",")
            .forEachIndexed { index, v ->
                map[index.toLong()] = v.toLong()
            }

        return map
    }

    fun part1(input: List<String>) {
        process(parse(input))
    }

    fun part2(input: List<String>) {
        process(parse(input))
    }

    val input = readInput("inputs/Day09")
    part1(input)
    part2(input)
}

fun MutableMap<Long, Long>.gets(i: Long): Long {
    if (i < 0) error("invalid access $i")
    return this[i] ?: 0L
}

fun MutableMap<Long, Long>.sets(i: Long, v: Long) {
    this[i] = v
}

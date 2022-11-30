fun main() {

    fun part1(input: List<String>): Int {
        val list = input.single()
            .chunked(25)
            .chunked(6)

        val layer = list.fold(list.first()) { acc, item ->
            if ((item count '0') < (acc count '0')) {
                item
            } else {
                acc
            }
        }

        return (layer count '1') * (layer count '2')
    }

    fun part2(input: List<String>): String {
        val list = input
            .single()
            .chunked(25)
            .chunked(6)
            .map {
                it.joinToString(separator = "")
            }

        val result = list.fold(list.first()) { tmp, item ->
            val acc = tmp.toMutableList()

            for (i in item.indices) {
                if (acc[i] == '2') {
                    acc[i] = item[i]
                }
            }

            acc.joinToString(separator = "")
        }

        result.chunked(25).forEach {
            println(it.map { c -> if (c == '1') "🟨" else "⬛" }.joinToString(""))
        }

        return result
    }

    val input = readInput("inputs/Day08")
    println(part1(input))
    println(part2(input))
}

infix fun List<String>.count(cc: Char) = sumOf { it.count { c -> c == cc } }

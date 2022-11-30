import kotlin.math.ceil

fun main() {

    data class Element(var amount: Int, val name: String)

    val buffer = mutableMapOf<String, Int>()
    lateinit var map: Map<Element, List<Element>>

    fun parseElement(it: String): Element {
        val (amount, name) = it.trim().split(" ")
        return Element(amount.toInt(), name)
    }

    fun elementsNeededFor(e: Element): List<Element> {
        val key = map.keys.find { key -> key.name == e.name }
        return map[key]!!
    }

    fun calcORE(e: Element): Int {
        return if (e.name == "ORE") {
            e.amount
        } else {
            val key = map.keys.find { key -> key.name == e.name }!!
            val elementNeeded = map[key]!!
            val currAmount = buffer.getOrDefault(e.name, 0)

            if (currAmount >= e.amount) {
                buffer[e.name] = currAmount - e.amount
                0
            } else if (currAmount > 0 && key.amount < e.amount) {
                buffer[e.name] = key.amount - e.amount
                ceil(currAmount.toDouble() + key.amount / e.amount).toInt() - currAmount
            } else {
                val amountToCreate = ceil(e.amount.toDouble() / (key.amount)).toInt()
                buffer[e.name] = (amountToCreate * (key.amount)) - e.amount

                amountToCreate * elementNeeded.sumOf {
                    val calcORE = calcORE(it)
                    println("$it $calcORE")
                    calcORE
                }
            }
        }
    }

    fun part1(input: List<String>): Int {
        map = input.map {
            it.split("=>")
                .map {
                    it.split(",").map { parseElement(it) }
                }
        }.associate { (first, second) ->
            second.single() to first
        }

        return calcORE(Element(1, "FUEL"))
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val input = readInput("test")
    println(part1(input))
    println(part2(input))
}




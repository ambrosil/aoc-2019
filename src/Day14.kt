import kotlin.math.ceil

fun main() {

    data class Element(var amount: Long, val name: String)

    fun parseElement(it: String): Element {
        val (amount, name) = it.trim().split(" ")
        return Element(amount.toLong(), name)
    }

    fun calcOre(
        reserves: MutableMap<String, Long>,
        dictionary: Map<Element, List<Element>>,
        wantedElements: List<Element>
    ): Long {
        val newWantedElements = wantedElements
            .flatMap { wantedElement ->
                if (wantedElement.name == "ORE") {
                    listOf(wantedElement)
                } else {
                    val actualQuantity = reserves.getOrDefault(wantedElement.name, 0L)
                    val dictionaryElement = dictionary.entries.single { it.key.name == wantedElement.name }
                    val amount = ceil((wantedElement.amount - actualQuantity).toDouble() / dictionaryElement.key.amount).toLong()

                    reserves[wantedElement.name] = actualQuantity + dictionaryElement.key.amount * amount - wantedElement.amount
                    dictionaryElement.value.map { Element(it.amount * amount, it.name) }
                }
            }
            .groupBy { it.name }
            .map { entry -> Element(entry.value.sumOf { it.amount }, entry.key) }

        return if (newWantedElements.size == 1) {
            newWantedElements[0].amount
        } else {
            calcOre(reserves, dictionary, newWantedElements)
        }
    }

    fun getDictionary(input: List<String>): Map<Element, List<Element>> {
        return input.map {
            it.split("=>")
                .map {
                    it.split(",")
                        .map { parseElement(it) }
                }
        }.associate { (first, second) ->
            second.single() to first
        }
    }

    fun calcOre(dictionary: Map<Element, List<Element>>, amount: Long): Long {
        val reserves = mutableMapOf<String, Long>()
        return calcOre(reserves, dictionary, listOf(Element(amount, "FUEL")))
    }

    fun part1(input: List<String>) = calcOre(getDictionary(input), 1)

    fun part2(input: List<String>): Long {
        val dictionary = getDictionary(input)

        val ore = calcOre(dictionary, 1)
        val estimate = (1000000000000 / ore)
        var count = 0

        do {
            val neededOre = calcOre(dictionary, ++count * estimate)
        } while (neededOre <= 1000000000000)

        var start = --count * estimate

        do {
            val neededOre = calcOre(dictionary, ++start)
        } while (neededOre <= 1000000000000)

        return start - 1
    }

    val input = readInput("inputs/test")
    println(part1(input))
    println(part2(input))
}




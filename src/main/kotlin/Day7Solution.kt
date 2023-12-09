import java.lang.Exception

class Day7Solution : Solution() {
    private val cardStrengthMapping : Map<Char, Int> = mapOf(
        '2' to 0,
        '3' to 1,
        '4' to 2,
        '5' to 3,
        '6' to 4,
        '7' to 5,
        '8' to 6,
        '9' to 7,
        'T' to 8,
        'J' to 9,
        'Q' to 10,
        'K' to 11,
        'A' to 12
    )

    override fun solvePt1() {
        val handTypes = mutableMapOf<String, HandType>()
        val handBids = mutableMapOf<String, Int>()
        for (line in input) {
            val handAndBid = line.trim().split(Regex("\\s+")) // sorry for not just splitting on whitespace on previous days I have realized the grave error of my ways
            val hand = handAndBid[0]
            val bid = handAndBid[1]
            handTypes[hand] = getHandType(hand)
            // sanity check
            if (handBids.containsKey(hand)) {
                throw Exception("oh god there are duplicates")
            }
            handBids[hand] = bid.toInt()
        }
        val allHandsSorted = handTypes.keys.sortedWith(
            compareBy<String> {
                handTypes[it]
            }.thenComparator { s1, s2 ->
                var firstDifferentCharIndex = -1
                for (i in s1.indices) {
                    if (s1[i] != s2[i]) {
                        firstDifferentCharIndex = i
                        break
                    }
                }
                // ascending
                compareValues(cardStrengthMapping[s1[firstDifferentCharIndex]],
                    cardStrengthMapping[s2[firstDifferentCharIndex]])
            })
//        println(allHandsSorted)
//        println(handBids)
        println(allHandsSorted.foldIndexed(0) {i, s, e -> s + ((i + 1) * handBids[e]!!)})
    }

    override fun solvePt2() {
        TODO("Not yet implemented")
    }

    private fun getHandType(hand : String) : HandType {
        val chars = hand.toCharArray()
        val labelCount = chars.associateWith{0}.toMutableMap()
        for (c in chars) {
            labelCount[c] = labelCount[c]!!.plus(1)
        }
        val cardTypes = labelCount.values
        val numCardTypes = cardTypes.size

        when (numCardTypes) {
            1 -> return HandType.FIVE_OF_A_KIND
            2 -> return if (cardTypes.contains(4)) {
                HandType.FOUR_OF_A_KIND
            } else {
                HandType.FULL_HOUSE
            }
            3 -> return if (cardTypes.contains(3)) {
                HandType.THREE_OF_A_KIND
            } else {
                HandType.TWO_PAIR
            }
            4 -> return HandType.ONE_PAIR
            5 -> return HandType.HIGH_CARD
            else -> throw Exception()
        }
    }

    enum class HandType {
        HIGH_CARD,
        ONE_PAIR,
        TWO_PAIR,
        THREE_OF_A_KIND,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        FIVE_OF_A_KIND
    }



}
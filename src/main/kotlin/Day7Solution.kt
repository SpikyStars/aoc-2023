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

    // :sob:
    private val cardStrengthMappingPt2 : Map<Char, Int> = mapOf(
        'J' to -1,
        '2' to 0,
        '3' to 1,
        '4' to 2,
        '5' to 3,
        '6' to 4,
        '7' to 5,
        '8' to 6,
        '9' to 7,
        'T' to 8,
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

    // copy paste because i am lazy
    override fun solvePt2() {
        val handTypes = mutableMapOf<String, HandType>()
        val handBids = mutableMapOf<String, Int>()
        for (line in input) {
            val handAndBid = line.trim().split(Regex("\\s+"))
            val hand = handAndBid[0]
            val bid = handAndBid[1]
            handTypes[hand] = getHandTypePt2(hand)
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
                compareValues(cardStrengthMappingPt2[s1[firstDifferentCharIndex]],
                    cardStrengthMappingPt2[s2[firstDifferentCharIndex]])
            })
//        println(allHandsSorted)
//        println(handBids)
        println(allHandsSorted.foldIndexed(0) {i, s, e -> s + ((i + 1) * handBids[e]!!)})
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

    private fun getHandTypePt2(hand : String) : HandType {
        val chars = hand.toCharArray()
        val labelCount = chars.associateWith{0}.toMutableMap()
        var jokerCount = 0

        for (c in chars) {
            labelCount[c] = labelCount[c]!!.plus(1)
            if (c == 'J') {
                jokerCount++
            }
        }
        val cardTypes = labelCount.values
        val numCardTypes = cardTypes.size

        when (numCardTypes) {
            1 -> return HandType.FIVE_OF_A_KIND
            2 -> {
                if (cardTypes.contains(4)) {
                    return if (jokerCount > 0) {
                        // AAJAA or JJAJJ
                        HandType.FIVE_OF_A_KIND
                    } else {
                        // AABAA
                        HandType.FOUR_OF_A_KIND
                    }
                } else {
                    return if (jokerCount > 0) {
                        // JJJAA or AAAJJ
                        HandType.FIVE_OF_A_KIND
                    } else {
                        // AAABB
                        HandType.FULL_HOUSE
                    }
                }
            }
            3 -> {
                return if (cardTypes.contains(3)) {
                    if (jokerCount > 0) {
                        // JJJAB or AAAJB
                        HandType.FOUR_OF_A_KIND
                    } else {
                        HandType.THREE_OF_A_KIND
                    }
                } else {
                    when (jokerCount) {
                        2 -> HandType.FOUR_OF_A_KIND // JJAAB
                        1 -> HandType.FULL_HOUSE // AABBJ
                        else -> HandType.TWO_PAIR // AABBC
                    }
                }
            }
            4 -> {
                return if (jokerCount > 0) {
                    // JJABC or AAJBC
                    HandType.THREE_OF_A_KIND
                } else {
                    // AABCD
                    HandType.ONE_PAIR
                }
            }
            5 -> {
                return if (jokerCount > 0) {
                    // JABCD
                    HandType.ONE_PAIR
                } else {
                    HandType.HIGH_CARD
                }
            }
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
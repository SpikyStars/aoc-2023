class Day4Solution : Solution() {

    override fun solvePt1() {
        var totalPoints = 0

        for (line in input) {
            var pointsForCard = 0
            val numberLists = line.replace(Regex("Card \\d+:\\s+"), "").split("|")
            val winningNums = numberLists[0].trim().replace(Regex("\\s+"), ",")
                .split(",").toSet()
            val yourNums = numberLists[1].trim().replace(Regex("\\s+"), ",").split(",")

            for (num in yourNums) {
                if (winningNums.contains(num)) {
                    if (pointsForCard == 0) {
                        pointsForCard = 1
                    } else {
                        pointsForCard *= 2
                    }
                }
            }

            totalPoints += pointsForCard
        }
        println(totalPoints)
    }

    override fun solvePt2() {
        var copiesPerCard = IntArray(input.size) {_ -> 1}

        for (i in input.indices) {
            val line = input[i]
            var numOfMatching = 0

            val numberLists = line.replace(Regex("Card \\d+:\\s+"), "").split("|")
            val winningNums = numberLists[0].trim().replace(Regex("\\s+"), ",")
                .split(",").toSet()
            val yourNums = numberLists[1].trim().replace(Regex("\\s+"), ",").split(",")

            // Count amount of matching numbers
            for (num in yourNums) {
                if (winningNums.contains(num)) {
                    numOfMatching++
                }
            }
//            println("Found $numOfMatching matches")

            // Add copies of next few cards
            for (j in 1 .. numOfMatching) {
                copiesPerCard[i + j] += copiesPerCard[i]
            }

        }
        println(copiesPerCard.fold(0) {s, e -> s + e})
    }
}
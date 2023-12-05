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
        TODO("Not yet implemented")
    }
}
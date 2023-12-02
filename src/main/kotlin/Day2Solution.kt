class Day2Solution : Solution() {
    override var dayNum = 2

    val maxRed = 12
    val maxGreen = 13
    val maxBlue = 14

    override fun solvePt1() {
        var sumPossibleGameIDs = 0

        for (line in input) {
            val gameIDAndInfo = line.split(":")
            val gameID = gameIDAndInfo[0].replace(Regex("[^\\d.]"), "").toInt()
            val allHands = gameIDAndInfo[1].split(";")

            var isPossible = true
            for (hand in allHands) {
                val cubeTypes = hand.split(", ")
                for (cubeType in cubeTypes) {
                    val numAndColor = cubeType.trim().split(" ")
                    val num = numAndColor[0].toInt()
                    val color = numAndColor[1]
                    if ((color == "red" && num > maxRed)
                        || (color == "green" && num > maxGreen)
                        || (color == "blue" && num > maxBlue)) {
                        isPossible = false
                        break
                    }
                }
                if (!isPossible) {
                    break
                }
            }

            if (isPossible) {
                sumPossibleGameIDs += gameID
            }

        }

        println(sumPossibleGameIDs)
    }

    override fun solvePt2() {
        TODO("Not yet implemented")
    }




}
class Day6Solution : Solution() {

    override fun solvePt1() {
        val raceTimes = input[0].replace("Time:", "").trim()
            .replace(Regex("\\s+"), ",").split(",")
        val distRecords = input[1].replace("Distance:", "").trim()
            .replace(Regex("\\s+"), ",").split(",")
        var marginOfError = 1

        for (i in raceTimes.indices) {
            val raceTime = raceTimes[i].toInt()
            val distRecord = distRecords[i].toInt()
            var winningPossibilities = 0

            for (t in 0..raceTime) {
                if ((t * (raceTime - t)) > distRecord) {
                    winningPossibilities++
                }
            }
            marginOfError *= winningPossibilities
        }
        println(marginOfError)
    }

    override fun solvePt2() {
        val raceTime = input[0].replace("Time:", "").trim()
            .replace(Regex("\\s+"), "").toLong()
        val distRecord = input[1].replace("Distance:", "").trim()
            .replace(Regex("\\s+"), "").toLong()
        var marginOfError = 1

//        var lowerBound =

//        for (t in 0 .. raceTime) {
//            if (t < )
//        }
    }

}
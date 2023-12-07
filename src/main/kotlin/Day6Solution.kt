import java.math.BigDecimal
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

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
            .replace(Regex("\\s+"), "").toDouble()
        val distRecord = input[1].replace("Distance:", "").trim()
            .replace(Regex("\\s+"), "").toDouble()

        // Equation: (1)t^2 - (raceTime)t + distRecord <= 0
        // (I think)
        // (I haven't done this for years)
        val bounds = solveQuadraticRounded(1.0, -raceTime, distRecord)
        val lowerBound = bounds.first
        val upperBound = bounds.second
        // +1 to be inclusive
        println(upperBound - lowerBound + 1)
    }

    private fun solveQuadraticRounded(a : Double, b : Double, c : Double) : Pair<Long, Long> {
        val discriminant = (b * b) - (4 * a * c)

        val bound1 = (-b - sqrt(discriminant)) / (2 * a)
        val bound2 = (-b + sqrt(discriminant)) / (2 * a)

        return Pair(ceil(bound1).toLong(), floor(bound2).toLong())

    }
}
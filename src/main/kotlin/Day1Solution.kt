class Day1Solution : Solution() {
    override var dayNum = 1

    override fun solvePt1() {
        var calibrationSum = 0
        for (line in input) {
            var lineNumericChars = line.replace(Regex("[^\\d.]"), "")
            var firstDigit = lineNumericChars.first().digitToInt()
            var lastDigit = lineNumericChars.last().digitToInt()
            calibrationSum += assembleCalibrationValue(firstDigit, lastDigit)
        }
        println(calibrationSum)
    }

    override fun solvePt2() {
        // Will actually properly separate out the pt1 and pt2 inputs if they are consistently different.
        // I don't remember what is most common right now.
        if (isTest) {
            loadInputPt2()
        }

        val numberMappings = mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9
        ) // sorry, that's as high as I can count :(

        var calibrationSum = 0

        for (line in input) {
            var earliestDigit = -1
            var latestDigit = -1

            var earliestIndex = Int.MAX_VALUE
            var latestIndex = Int.MIN_VALUE

            for (key in numberMappings.keys) {
                val firstNumLocInString = line.indexOf(key)
                val lastNumLocInString = line.lastIndexOf(key)
                if (firstNumLocInString != -1 && firstNumLocInString < earliestIndex) {
                    earliestIndex = firstNumLocInString
                    earliestDigit = numberMappings[key]!!
                }
                if (lastNumLocInString != -1 && lastNumLocInString > latestIndex) {
                    latestIndex = lastNumLocInString
                    latestDigit = numberMappings[key]!!
                }
            }

            val firstDigitIndex = line.indexOfFirst { c -> c.isDigit() }
            if (firstDigitIndex != -1 && firstDigitIndex < earliestIndex) {
                // Last thing we are checking, so we don't need to update the index variable at this point.
                earliestDigit = line[firstDigitIndex].digitToInt()
            }
            val lastDigitIndex = line.indexOfLast { c -> c.isDigit() }
            if (lastDigitIndex != -1 && lastDigitIndex > latestIndex) {
                latestDigit = line[lastDigitIndex].digitToInt()
            }
//            println("Adding $earliestDigit$latestDigit to sum")
            calibrationSum += assembleCalibrationValue(earliestDigit, latestDigit)
        }
        println(calibrationSum)
    }

    private fun assembleCalibrationValue(a : Int, b : Int): Int {
        return (a * 10) + b
    }
}
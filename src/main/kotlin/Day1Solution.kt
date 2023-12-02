class Day1Solution : Solution() {
    override var dayNum = 1

    override fun solvePt1() {
        var calibrationSum = 0
        for (line in input) {
            var lineNumericChars = line.replace(Regex("[^\\d.]"), "")
            var firstDigit = lineNumericChars.first()
            var lastDigit = lineNumericChars.last()
            val stringBuilder = StringBuilder()
            stringBuilder.append(firstDigit)
            stringBuilder.append(lastDigit)
            calibrationSum += stringBuilder.toString().toInt()
        }
        println(calibrationSum)
    }

    override fun solvePt2() {
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


    }
}
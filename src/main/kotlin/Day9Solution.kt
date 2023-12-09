class Day9Solution : Solution() {
    override fun solvePt1() {
        var sumExtrapolated = 0

        for (line in input) {
            val values = line.trim().split(Regex("\\s+")).map { v -> v.toInt() }
            var currentSequence = values
            val differenceSequences : MutableList<List<Int>> = mutableListOf(currentSequence)

            while (!currentSequence.all { it == 0 }) {
                currentSequence = getDifferenceSequence(currentSequence)
                differenceSequences.add(currentSequence)
            }
           //extrapolate
            var extrapolatedValue = 0
            for (sequence in differenceSequences.reversed()) {
                extrapolatedValue += sequence.last()
            }
            sumExtrapolated += extrapolatedValue
        }
        println(sumExtrapolated)
    }

    override fun solvePt2() {
        var sumExtrapolated = 0

        for (line in input) {
            val values = line.trim().split(Regex("\\s+")).map { v -> v.toInt() }
            var currentSequence = values
            val differenceSequences : MutableList<List<Int>> = mutableListOf(currentSequence)

            while (!currentSequence.all { it == 0 }) {
                currentSequence = getDifferenceSequence(currentSequence)
                differenceSequences.add(currentSequence)
            }
            //extrapolate
            var extrapolatedValue = 0
            for (sequence in differenceSequences.reversed()) {
                extrapolatedValue = sequence.first() - extrapolatedValue
//                println(extrapolatedValue)
            }
            sumExtrapolated += extrapolatedValue
        }
        println(sumExtrapolated)
    }

    private fun getDifferenceSequence(values : List<Int>) : List<Int> {
        val differenceSequence : MutableList<Int> = mutableListOf()
        for (i in 0..< (values.size - 1)) {
            differenceSequence.add(values[i + 1] - values[i])
        }
//        println(differenceSequence)
        return differenceSequence
    }
}
class Day3Solution : Solution() {
    override var dayNum = 3

    override fun solvePt1() {
        var sumPartNums = 0

        for (i in input.indices) {
            val curRow = input[i]
            var curPartNumDetected = false
            var numBuffer = ""

            for (j in curRow.toCharArray().indices) {
                val curChar = curRow[j]
                if (curChar.isDigit()) {
                    numBuffer += curChar
                    if (!curPartNumDetected) {
                        curPartNumDetected = checkNeighborsForSymbol(i, j)
                    }
                } else {
                    if (curPartNumDetected) {
                        sumPartNums += numBuffer.toInt()
//                        println("Adding $numBuffer to the sum")
                    }
                    curPartNumDetected = false
                    numBuffer = ""
                }
            }
            // If line has ended and there was a number right at the end
            if (curPartNumDetected) {
                sumPartNums += numBuffer.toInt()
            }
        }
        println(sumPartNums)
    }

    override fun solvePt2() {
        var sumGearRatios = 0
        val asteriskLocations : MutableSet<Pair<Int, Int>> = mutableSetOf()
        val numsAdjacentToAsterisk : MutableMap<Pair<Int, Int>, MutableList<String>> = mutableMapOf()

        // Initial assignment of asterisk locations.
        for (i in input.indices) {
            val curRow = input[i]

            for (j in curRow.toCharArray().indices) {
                val curChar = curRow[j]
                if (curChar == '*') {
                    val coords = Pair(i, j)
                    asteriskLocations.add(coords)
                    numsAdjacentToAsterisk[coords] = mutableListOf()
                }
            }
        }
        for (i in input.indices) {
            val curRow = input[i]
            var numBuffer = ""
            var asteriskCoords : Pair<Int, Int>? = null

            for (j in curRow.toCharArray().indices) {
                val curChar = curRow[j]
                if (curChar.isDigit()) {
                    numBuffer += curChar
                    if (asteriskCoords == null) {
                        asteriskCoords = checkNeighborsForAsterisk(i, j)
                    }
                } else {
                    if (asteriskCoords != null) {
                        numsAdjacentToAsterisk[asteriskCoords]!!.add(numBuffer)
                    }
                    asteriskCoords = null
                    numBuffer = ""
                }
            }
            // If line has ended and there was a number right at the end
            if (asteriskCoords != null) {
                numsAdjacentToAsterisk[asteriskCoords]!!.add(numBuffer)
            }
        }

        for (neighboringNums in numsAdjacentToAsterisk.values) {
            if (neighboringNums.size == 2) {
                sumGearRatios += (neighboringNums[0].toInt() * neighboringNums[1].toInt())
            }
        }
        println(sumGearRatios)
    }

    private fun checkNeighborsForSymbol(rowIndex : Int, colIndex : Int) : Boolean {
        val curRow = input[rowIndex]
        if (colIndex > 0) {
            if (isValidSymbol(input[rowIndex][colIndex - 1])) { // W
//                println("Symbol found to W")
                return true
            }
            if (rowIndex > 0) {
                if (isValidSymbol(input[rowIndex-1][colIndex-1])) { // NW
//                    println("Symbol found to NW")
                    return true
                }
            }
            if (rowIndex < curRow.length - 1) {
                if (isValidSymbol(input[rowIndex+1][colIndex-1])) { // SW
//                    println("Symbol found to SW")
                    return true
                }
            }
        }
        if (rowIndex > 0) {
            if (isValidSymbol(input[rowIndex-1][colIndex])) { // N
//                println("Symbol found to N")
                return true
            }
        }
        if (rowIndex < input.size - 1) {
            if (isValidSymbol(input[rowIndex+1][colIndex])) { // S
//                println("Symbol found to S")
                return true
            }
        }
        if (colIndex < curRow.length - 1) {
            if (isValidSymbol(input[rowIndex][colIndex + 1])) { // E
//                println("Symbol found to E")
                return true
            }
            if (rowIndex < input.size - 1) {
                if (isValidSymbol(input[rowIndex+1][colIndex+1])) { // SE
//                    println("Symbol found to SE")
                    return true
                }
            }
            if (rowIndex > 0) {
                if (isValidSymbol(input[rowIndex-1][colIndex+1])) { // NE
//                    println("Symbol found to NE")
                    return true
                }
            }
        }
//        println("No symbol found")
        return false
    }

    private fun isValidSymbol(c : Char) : Boolean {
        return !c.isLetterOrDigit() && c != '.'
    }

    // mmm copy paste
    private fun checkNeighborsForAsterisk(rowIndex : Int, colIndex : Int) : Pair<Int, Int>? {
        val curRow = input[rowIndex]
        if (colIndex > 0) {
            if (input[rowIndex][colIndex - 1] == '*') { // W
//                println("Symbol found to W")
                return Pair(rowIndex, colIndex - 1)
            }
            if (rowIndex > 0) {
                if (input[rowIndex-1][colIndex-1] == '*') { // NW
//                    println("Symbol found to NW")
                    return Pair(rowIndex - 1, colIndex - 1)
                }
            }
            if (rowIndex < curRow.length - 1) {
                if (input[rowIndex+1][colIndex-1] == '*') { // SW
//                    println("Symbol found to SW")
                    return Pair(rowIndex + 1, colIndex - 1)
                }
            }
        }
        if (rowIndex > 0) {
            if (input[rowIndex-1][colIndex] == '*') { // N
//                println("Symbol found to N")
                return Pair(rowIndex - 1, colIndex)
            }
        }
        if (rowIndex < input.size - 1) {
            if (input[rowIndex+1][colIndex] == '*') { // S
//                println("Symbol found to S")
                return Pair(rowIndex + 1, colIndex)
            }
        }
        if (colIndex < curRow.length - 1) {
            if (input[rowIndex][colIndex + 1] == '*') { // E
//                println("Symbol found to E")
                return Pair(rowIndex, colIndex + 1)
            }
            if (rowIndex < input.size - 1) {
                if (input[rowIndex+1][colIndex+1] == '*') { // SE
//                    println("Symbol found to SE")
                    return Pair(rowIndex + 1, colIndex + 1)
                }
            }
            if (rowIndex > 0) {
                if (input[rowIndex-1][colIndex+1] == '*') { // NE
//                    println("Symbol found to NE")
                    return Pair(rowIndex - 1, colIndex + 1)
                }
            }
        }
//        println("No symbol found")
        return null
    }

}
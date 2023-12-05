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
        TODO("Not yet implemented")
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

}
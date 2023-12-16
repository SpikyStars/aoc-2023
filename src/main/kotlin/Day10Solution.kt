class Day10Solution : Solution() {
    private val pipeGrid : MutableList<List<Pipe?>> = mutableListOf()
    private lateinit var startPos : Pair<Int, Int> // row, col
    private val pipeTypesConnectingNorth : Set<PipeType> = mutableSetOf(PipeType.VERTICAL, PipeType.NW_BEND, PipeType.NE_BEND)
    private val pipeTypesConnectingSouth : Set<PipeType> = mutableSetOf(PipeType.VERTICAL, PipeType.SW_BEND, PipeType.SE_BEND)
    private val pipeTypesConnectingWest : Set<PipeType> = mutableSetOf(PipeType.HORIZONTAL, PipeType.NW_BEND, PipeType.SW_BEND)
    private val pipeTypesConnectingEast : Set<PipeType> = mutableSetOf(PipeType.HORIZONTAL, PipeType.NE_BEND, PipeType.SE_BEND)

    override fun solvePt1() {
        // populate pipe grid
        for ((row, line) in input.withIndex()) {
            val pipeRow : MutableList<Pipe?> = mutableListOf()
            for ((col, c) in line.withIndex()) {
                when (c) {
                    'S' -> {
                        val startingPipe = Pipe(true)
                        startPos = Pair(row, col)
                        pipeRow.add(startingPipe)
                    }
                    '.' -> pipeRow.add(null)
                    else -> {
                        val pipeToAdd = Pipe()
                        pipeToAdd.pipeType = getPipeType(c)
                        pipeRow.add(pipeToAdd)
                    }
                }
            }
            pipeGrid.add(pipeRow)
        }
        // process pipe connections except start
        val startingPipe = pipeGrid[startPos.first][startPos.second]
        val possibleStartConnections : MutableSet<Pair<Int, Int>> = mutableSetOf()
        for ((row, pipeLine) in pipeGrid.withIndex()) {
            for ((col, pipe) in pipeLine.withIndex()) {
                processPipeConnections(pipe, row, col, possibleStartConnections)
            }
        }
        // set start pipe connections
        setStartPipeConnections(possibleStartConnections)

        // find longest path
        val pipesVisited : MutableSet<Pipe> = mutableSetOf()
        val pipesToVisit : MutableList<Pipe> = startingPipe!!.connectingPipes.toMutableList()
        var longestSoFar = 0


        while (pipesToVisit.isNotEmpty()) {
            longestSoFar++
            val newPipesToVisit : MutableList<Pipe> = mutableListOf()
            for (pipe in pipesToVisit) {
                for (neighborPipe in pipe.connectingPipes) {
                    if (!pipesVisited.contains(neighborPipe)) {
                        newPipesToVisit.add(neighborPipe)
                    }
                }
            }
            pipesVisited.addAll(pipesToVisit)
            pipesToVisit.clear()
            pipesToVisit.addAll(newPipesToVisit)

        }

        println(longestSoFar)
    }

    override fun solvePt2() {
        TODO("Not yet implemented")
    }

    class Pipe (val isStart : Boolean = false) {
        lateinit var pipeType : PipeType
        var connectingPipes : MutableSet<Pipe> = mutableSetOf()
    }

    private fun getPipeType(c : Char) : PipeType {
        return when (c) {
            '|' -> PipeType.VERTICAL
            '-' -> PipeType.HORIZONTAL
            'L' -> PipeType.NE_BEND
            'J' -> PipeType.NW_BEND
            '7' -> PipeType.SW_BEND
            'F' -> PipeType.SE_BEND
            else -> throw Exception()
        }
    }

    // ungodly method I extracted out. thinking is too hard
    private fun processPipeConnections(
        pipe: Pipe?,
        row: Int,
        col: Int,
        possibleStartConnections: MutableSet<Pair<Int, Int>>
    ) {
        if (pipe == null || pipe.isStart) {
            return
        }

        var pipeToCheck: Pipe?
        if (pipeTypesConnectingNorth.contains(pipe.pipeType) && row > 0) {
            pipeToCheck = pipeGrid[row - 1][col]
            if (pipeToCheck == null) {
                return
            }
            if (pipeToCheck.isStart) {
                possibleStartConnections.add(Pair(row, col))
            } else if (pipeTypesConnectingSouth.contains(pipeToCheck.pipeType)) {
                pipe.connectingPipes.add(pipeToCheck)
            }
        }
        if (pipeTypesConnectingSouth.contains(pipe.pipeType) && row < pipeGrid.size - 1) {
            pipeToCheck = pipeGrid[row + 1][col]
            if (pipeToCheck == null) {
                return
            }
            if (pipeToCheck.isStart) {
                possibleStartConnections.add(Pair(row, col))
            } else if (pipeTypesConnectingNorth.contains(pipeToCheck.pipeType)) {
                pipe.connectingPipes.add(pipeToCheck)
            }
        }
        if (pipeTypesConnectingWest.contains(pipe.pipeType) && col != 0) {
            pipeToCheck = pipeGrid[row][col - 1]
            if (pipeToCheck == null) {
                return
            }
            if (pipeToCheck.isStart) {
                possibleStartConnections.add(Pair(row, col))
            } else if (pipeTypesConnectingEast.contains(pipeToCheck.pipeType)) {
                pipe.connectingPipes.add(pipeToCheck)
            }
        }
        if (pipeTypesConnectingEast.contains(pipe.pipeType) && col < pipeGrid.first().size - 1) {
            pipeToCheck = pipeGrid[row][col + 1]
            if (pipeToCheck == null) {
                return
            }
            if (pipeToCheck.isStart) {
                possibleStartConnections.add(Pair(row, col + 1))
            } else if (pipeTypesConnectingWest.contains(pipeToCheck.pipeType)) {
                pipe.connectingPipes.add(pipeToCheck)
            }
        }
    }

    private fun setStartPipeConnections (possibleStartConnections : Set<Pair<Int, Int>>) {
        val startingPipe = pipeGrid[startPos.first][startPos.second] ?: throw Exception()
        for (pipeLocation in possibleStartConnections) {
            val connectingPipe = pipeGrid[pipeLocation.first][pipeLocation.second] ?: throw Exception()
            if (pipeLocation.first > startPos.first && !pipeTypesConnectingNorth.contains(connectingPipe.pipeType)) {
                continue
            } else if (pipeLocation.first < startPos.first && !pipeTypesConnectingSouth.contains(connectingPipe.pipeType)) {
                continue
            } else if (pipeLocation.second > startPos.second && !pipeTypesConnectingWest.contains(connectingPipe.pipeType)) {
                continue
            } else if (pipeLocation.second < startPos.second && !pipeTypesConnectingEast.contains(connectingPipe.pipeType)) {
                continue
            }
            startingPipe.connectingPipes.add(connectingPipe)
        }
        assert(startingPipe.connectingPipes.size == 2)
    }


    enum class PipeType {
        VERTICAL,
        HORIZONTAL,
        NE_BEND,
        NW_BEND,
        SW_BEND,
        SE_BEND
    }
}
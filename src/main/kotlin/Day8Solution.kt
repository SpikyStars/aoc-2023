class Day8Solution : Solution() {
    override fun solvePt1() {
        val instructions = input[0]

        // Initialize node mapping
        val nodeNeighborMapping : MutableMap<String, List<String>> = mutableMapOf()
        for (line in input.drop(2)) {
            val nodeDeclaration = line.split("=")
            val nodeName = nodeDeclaration[0].trim()
            val nodeNeighbors = nodeDeclaration[1].trim().replace(Regex("[()]"), "")
                .split(",").map { n -> n.trim() }
            nodeNeighborMapping[nodeName] = nodeNeighbors
        }
        // Initialize node objects
        val nodeNameMapping : MutableMap<String, Node> = mutableMapOf()
        for (nodeName in nodeNeighborMapping.keys) {
            nodeNameMapping[nodeName] = Node(nodeName)
        }

        // Set node neighbors
        for (node in nodeNameMapping.values) {
            // ZZZ is always the end. Instead of setting left and right to itself we can keep it null.
            if (node.name != "ZZZ") {
                node.left = nodeNameMapping[nodeNeighborMapping[node.name]!![0]]
                node.right = nodeNameMapping[nodeNeighborMapping[node.name]!![1]]
            }
        }

        val startingNode = nodeNameMapping.getValue("AAA")
        println(getNumStepsFromDirections(startingNode, instructions))

    }

    override fun solvePt2() {
        TODO("Not yet implemented")
    }

    private fun getNumStepsFromDirections(startNode : Node, directions : String) : Int {
        var numSteps = 0
        var currentNode : Node? = startNode
        while (currentNode!!.name != "ZZZ") {
            val direction = directions[numSteps % directions.length]
            currentNode = if (direction == 'R') {
                currentNode.right
            } else {
                currentNode.left
            }
//            println("Took $direction. Moved to " + currentNode!!.name)
            numSteps++
        }
        return numSteps
    }

    class Node (val name: String) {
        var left : Node? = null
        var right : Node? = null

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Node

            return name == other.name
        }

        override fun hashCode(): Int {
            return name.hashCode()
        }
    }
}
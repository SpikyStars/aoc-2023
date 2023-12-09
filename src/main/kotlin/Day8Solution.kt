class Day8Solution : Solution() {
    private val nodeNeighborMapping : MutableMap<String, List<String>> = mutableMapOf()
    private val nodeNameMapping : MutableMap<String, Node> = mutableMapOf()

    private lateinit var directions : String


    override fun solvePt1() {
        directions = input[0]

        // Initialize node mapping
        for (line in input.drop(2)) {
            val nodeDeclaration = line.split("=")
            val nodeName = nodeDeclaration[0].trim()
            val nodeNeighbors = nodeDeclaration[1].trim().replace(Regex("[()]"), "")
                .split(",").map { n -> n.trim() }
            nodeNeighborMapping[nodeName] = nodeNeighbors
        }
        // Initialize node objects
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
        println(getNumStepsFromDirections(startingNode, directions))

    }

    override fun solvePt2() {
        val startingNodes : MutableList<Node> = nodeNameMapping.values.filter {
            it.name.endsWith("A")
        }.toMutableList()

        var numSteps = 0

        var currentNodes = startingNodes
        while (!areAllPathsComplete(currentNodes)) {
            val newNodes : MutableList<Node> = mutableListOf()
            val direction = directions[numSteps % directions.length]
            for (currentNode in currentNodes) {
                if (direction == 'R') {
                    currentNode.right?.let { newNodes.add(it) }
                } else {
                    currentNode.left?.let { newNodes.add(it) }
                }
            }
            numSteps++
            currentNodes = newNodes
            println(newNodes.toString())
        }
        println(numSteps)
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

    private fun areAllPathsComplete(nodes : List<Node>) : Boolean {
        return nodes.all { n -> n.name.endsWith("Z") }
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

        override fun toString(): String {
            return "Node(name='$name')"
        }


    }
}
abstract class Solution {
    lateinit var input : List<String>
    abstract var dayNum : Int
    var isTest = false

    fun loadInputAndSolve() {
        loadInput()
        solvePt1()
    }

    abstract fun solvePt1()

    abstract fun solvePt2()

    private fun loadInput() {
        val dir = if (isTest) "test" else "full"
        input = Solution::class.java.getResource("/inputs/$dir/day$dayNum")!!.readText().lines()
    }
}
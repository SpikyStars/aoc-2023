abstract class Solution {
    lateinit var input : List<String>
    abstract var dayNum : Int
    var isTest = false

    fun loadInputAndSolve() {
        loadInput()
        solvePt1()
        solvePt2()
    }

    abstract fun solvePt1()

    abstract fun solvePt2()

    private fun loadInput() {
        val dir = if (isTest) "test" else "full"
        input = Solution::class.java.getResource("/inputs/$dir/day$dayNum")!!.readText().lines()
    }

    protected fun loadInputPt2() {
        val dir = if (isTest) "test" else "full"
        input = Solution::class.java.getResource("/inputs/$dir/day$dayNum" + "pt2")!!.readText().lines()
    }
}
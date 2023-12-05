abstract class Solution {
    lateinit var input : List<String>
    var isTest = false
    lateinit var day : String

    fun loadInputAndSolve(day : String) {
        this.day = day
        loadInput()
        solvePt1()
        solvePt2()
    }

    abstract fun solvePt1()

    abstract fun solvePt2()

    private fun loadInput() {
        val dir = if (isTest) "test" else "full"
        input = Solution::class.java.getResource("/inputs/$dir/$day")!!.readText().lines()
    }

    protected fun loadInputPt2() {
        val dir = if (isTest) "test" else "full"
        input = Solution::class.java.getResource("/inputs/$dir/day$day" + "pt2")!!.readText().lines()
    }
}
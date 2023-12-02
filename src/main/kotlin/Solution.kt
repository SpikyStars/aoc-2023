abstract class Solution {
    lateinit var input : String
    abstract var dayNum : Int
    var isTest = false

    fun loadInputAndSolve() {
        loadInput()
        solve()
    }

    abstract fun solve()

    private fun loadInput() {
        val dir = if (isTest) "test" else "full"
        input = Solution::class.java.getResource("/inputs/$dir/day$dayNum")!!.readText()
    }
}
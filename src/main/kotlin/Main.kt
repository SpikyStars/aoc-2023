import kotlin.system.exitProcess

val solutionMappings = mapOf(
    "day1" to Day1Solution(),
    "day2" to Day2Solution(),
    "day3" to Day3Solution(),
    "day4" to Day4Solution(),
    "day5" to Day5Solution(),
    "day6" to Day6Solution(),
    "day7" to Day7Solution(),
    "day8" to Day8Solution(),
    )

fun main(args: Array<String>) {
    // Takes in the day (formatted as day1, day2, etc), and optional test flag
    // eg. "day1 test" or "day2"
    if (args.isEmpty()) {
        println("Please specify day to run!")
        exitProcess(1)
    }
    val daySolution = solutionMappings.getOrElse(args[0]) {
        println("I don't have that day implemented!")
        exitProcess(1)
    }
    if (args.size > 1 && args[1] == "test") {
        daySolution.isTest = true
    }
    daySolution.loadInputAndSolve(args[0])
}
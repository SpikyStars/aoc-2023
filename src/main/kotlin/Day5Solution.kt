class Day5Solution : Solution() {
    private val seedList = mutableListOf<UInt>()

    private val seedToSoilMappings = mutableListOf<NumberMapping>()
    private val soilToFertilizerMappings = mutableListOf<NumberMapping>()
    private val fertilizerToWaterMappings = mutableListOf<NumberMapping>()
    private val waterToLightMappings = mutableListOf<NumberMapping>()
    private val lightToTemperatureMappings = mutableListOf<NumberMapping>()
    private val temperatureToHumidityMappings = mutableListOf<NumberMapping>()
    private val humidityToLocationMappings = mutableListOf<NumberMapping>()

    override fun solvePt1() {
        // Initial processing.
        var currentCollection : MutableList<NumberMapping>? = null
        for (line in input) {

            if (line.isEmpty()) {
                continue
            }

            if (line.first().isDigit()) {
                val nums = line.trim()
                    .replace(Regex("\\s+"), ",").split(",").map { n -> n.toUInt() }
                val destStart = nums[0]
                val sourceStart = nums[1]
                val rangeLength = nums[2]

                currentCollection!!.add(NumberMapping(sourceStart, destStart, rangeLength))

            } else if (line.startsWith("seeds:")) {
                val seeds = line.replace("seeds: ", "").trim()
                    .replace(Regex("\\s+"), ",").split(",")
                seedList.addAll(seeds.map{s -> s.toUInt()})
            } else {
                currentCollection = when {
                    line.startsWith("seed-to-soil map") -> seedToSoilMappings
                    line.startsWith("soil-to-fertilizer map") -> soilToFertilizerMappings
                    line.startsWith("fertilizer-to-water map") -> fertilizerToWaterMappings
                    line.startsWith("water-to-light map") -> waterToLightMappings
                    line.startsWith("light-to-temperature map") -> lightToTemperatureMappings
                    line.startsWith("temperature-to-humidity map") -> temperatureToHumidityMappings
                    line.startsWith("humidity-to-location map") -> humidityToLocationMappings
                    else -> null
                }
            }
        }

        println(seedList.minOfOrNull { s -> findSeedLocation(s) })
    }

    override fun solvePt2() {
        TODO("Not yet implemented")
    }

    private fun findSeedLocation(seed: UInt): UInt {
        var seedSoil = mapNumberUsingMappings(seedToSoilMappings, seed)
        var seedFertilizer = mapNumberUsingMappings(soilToFertilizerMappings, seedSoil)
        var seedWater = mapNumberUsingMappings(fertilizerToWaterMappings, seedFertilizer)
        var seedLight = mapNumberUsingMappings(waterToLightMappings, seedWater)
        var seedTemp = mapNumberUsingMappings(lightToTemperatureMappings, seedLight)
        var seedHumidity = mapNumberUsingMappings(temperatureToHumidityMappings, seedTemp)
        return mapNumberUsingMappings(humidityToLocationMappings, seedHumidity)
    }

    private fun mapNumberUsingMappings(mappings : MutableList<NumberMapping>, num : UInt) : UInt {
        return mappings.find { e -> e.numInRange(num) }?.getMappedValue(num) ?: num
    }

    class NumberMapping(private val sourceStart : UInt,
                        val destStart : UInt,
                        private val rangeLength : UInt) {

        fun numInRange(num : UInt) : Boolean {
            return num >= sourceStart && num < sourceStart + rangeLength
        }

        fun getMappedValue(num : UInt) : UInt {
            return destStart + (num - sourceStart)
        }

    }
}
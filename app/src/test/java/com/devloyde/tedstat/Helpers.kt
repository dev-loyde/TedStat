package com.devloyde.tedstat

import com.devloyde.tedstat.models.GlobalStat
import com.devloyde.tedstat.models.StatCountries
import com.devloyde.tedstat.utils.StatUtils
import org.junit.Test

import org.junit.Assert.*


class StatUtilsTest {

    @Test
    fun convertStringToInteger() {
        val expected = 100000000
        val actual = "100,000,000"
        val result = StatUtils.parseIntegerStat(actual)
        assertEquals(expected, result)
    }

    @Test
    fun globalStatPercentage_isCorrect() {
        val expected = 70
        val result = StatUtils.parseGlobalStatPercentage(700, 1000)
        assertEquals(expected, result)
    }

    @Test
    fun parseCountriesStat() {
        val visibilityFactor = 10000F
        val expected = listOf<Float>(
            61000F + visibilityFactor, //recovered
            65000F + visibilityFactor, //cases
            1200F + visibilityFactor //deaths
        )
        val country = StatCountries(
            1,
            null,
            "Nigeria",
            "65,000",
            "61,000",
            "1,200"
        )
        val result = StatUtils.parseCountriesStat(country)
        assertEquals(expected[0], result[0])
        assertEquals(expected[1], result[1])
        assertEquals(expected[2], result[2])
    }

    @Test
    fun parseGlobalStat() {
        val visibilityFactor = 20
        val expected = GlobalStat(
            1,
            "1,000",
            "2000",
            "500",
            100,
            71 + visibilityFactor - 5,
            14 + visibilityFactor
        )
        val result = StatUtils.parseGlobalStat(GlobalStat(1, "1,000", "2000", "500"))
        assertEquals(expected.id, result.id)
        assertEquals(expected.cases, result.cases)
        assertEquals(expected.recovered, result.recovered)
        assertEquals(expected.deaths, result.deaths)
        assertEquals(expected.casesProgress, result.casesProgress)
        assertEquals(expected.recoveredProgress, result.recoveredProgress)
        assertEquals(expected.deathsProgress, result.deathsProgress)
    }
}

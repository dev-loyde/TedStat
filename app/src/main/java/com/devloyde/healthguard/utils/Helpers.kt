package com.devloyde.healthguard.utils

import com.devloyde.healthguard.models.GlobalStat
import com.devloyde.healthguard.models.StatCountries

object StatUtils {
    private fun parseIntegerStat(text: String): Int {
        return text.replace(Regex(","), "").toInt()
    }

    private fun parseGlobalStatPercentage(stat: Int, total: Int): Int {
        return ((stat.toFloat() / total) * 100).toInt()
    }

    fun parseCountriesStat(stat: StatCountries): List<Float> {
        var confirmedCases = 0
        var recoveredCases = 0
        var deathCases = 0
        val visibilityFactor = 10000

        if (stat.recovered != null) {
            if (stat.recovered != "No data") {
                recoveredCases = stat.recovered.toIntOrNull() ?: parseIntegerStat(stat.recovered)
                recoveredCases += visibilityFactor
            }
        }
        if (stat.cases != null) {
            if (stat.cases != "No data") {
                confirmedCases = stat.cases.toIntOrNull() ?: parseIntegerStat(stat.cases)
                confirmedCases += visibilityFactor
            }
        }
        if (stat.deaths != null) {
            if (stat.deaths != "No data") {
                deathCases = stat.deaths.toIntOrNull() ?: parseIntegerStat(stat.deaths)
                deathCases += visibilityFactor
            }
        }

        return listOf(
            recoveredCases.toFloat(),
            confirmedCases.toFloat(),
            deathCases.toFloat()
        )

    }

    fun parseGlobalStat(stat: GlobalStat): GlobalStat {
        var confirmedCases = 0
        var recoveredCases = 0
        var deathCases = 0
        val visibilityFactor = 20

        if (stat.recovered != null) {
            if (stat.recovered != "No data") {
                recoveredCases = stat.recovered.toIntOrNull() ?: parseIntegerStat(stat.recovered)
            }
        }
        if (stat.cases != null) {
            if (stat.cases != "No data") {
                confirmedCases = stat.cases.toIntOrNull() ?: parseIntegerStat(stat.cases)
            }
        }
        if (stat.deaths != null) {
            if (stat.deaths != "No data") {
                deathCases = stat.deaths.toIntOrNull() ?: parseIntegerStat(stat.deaths)
            }
        }

        val total = confirmedCases + recoveredCases + deathCases
        val globalRecoveredProgress =
            parseGlobalStatPercentage(recoveredCases + deathCases, total) + visibilityFactor - 5
        val globalConfirmedProgress =
            parseGlobalStatPercentage(recoveredCases + confirmedCases + deathCases, total)
        val globalDeathsProgress = parseGlobalStatPercentage(deathCases, total) + visibilityFactor

        return GlobalStat(
            stat.id,
            stat.cases,
            stat.recovered,
            stat.deaths,
            globalConfirmedProgress, //confirmed cases will indicate full-bar on chart
            globalRecoveredProgress,
            globalDeathsProgress
        )

    }
}
package com.devloyde.tedstat.utils

import com.devloyde.tedstat.models.GlobalStat
import com.devloyde.tedstat.models.StatCountries

object StatUtils {

    fun parseIntegerStat(text: String): Int {
        return text.replace(Regex(","), "").toInt()
    }

    fun parseGlobalStatPercentage(stat: Int, total: Int): Int {
        return ((stat.toFloat() / total) * 100).toInt()
    }

    fun parseCountriesStat(stat: StatCountries): List<Float> {
        var confirmedCases = 0
        var recoveredCases = 0
        var deathCases = 0
        val visibilityFactor = 100

        if (stat.deaths != null) {
            if (stat.deaths != "No data") {
                deathCases = stat.deaths.toIntOrNull() ?: parseIntegerStat(stat.deaths)
                if (deathCases > visibilityFactor) {
                    deathCases += 10000
                }
            }
        }
        if (stat.recovered != null) {
            if (stat.recovered != "No data") {
                recoveredCases = stat.recovered.toIntOrNull() ?: parseIntegerStat(stat.recovered)
                if (deathCases > visibilityFactor) {
                    recoveredCases += 10000
                }
            }
        }
        if (stat.cases != null) {
            if (stat.cases != "No data") {
                confirmedCases = stat.cases.toIntOrNull() ?: parseIntegerStat(stat.cases)
                if (deathCases > visibilityFactor) {
                    confirmedCases += 10000
                }
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
package com.devloyde.healthguard.utils

object StatUtils{
    private fun parseIntegerStat(text: String): Int {
        return text.replace(Regex(","), "").toInt()
    }

    private fun parseFloatStat(text: String): Float {
        return text.replace(Regex(","), "").toFloat()
    }

    private fun parseGlobalStat(stat: String, total: Int): Int {
        return ((parseFloatStat(stat) / total) * 100).toInt()
    }

}
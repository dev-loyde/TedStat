package com.devloyde.healthguard.listeners

import com.devloyde.healthguard.models.StatCountries

class DisplayListener {

    interface CountrySelection {
        fun showCountrySelectionDialog()
    }

    interface UpdateCountrySelection {
        fun updateSelectedCountry(country: StatCountries)
    }
}
package com.devloyde.tedstat.listeners

import com.devloyde.tedstat.models.StatCountries

class DisplayListener {

    interface CountrySelection {
        fun showCountrySelectionDialog()
    }

    interface UpdateCountrySelection {
        fun updateSelectedCountry(country: StatCountries)
    }

    interface UpdateTheme {
        fun changeTheme(mode: Boolean)
    }


}
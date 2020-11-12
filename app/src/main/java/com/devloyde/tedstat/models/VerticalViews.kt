package com.devloyde.tedstat.models

import android.os.Parcel
import android.os.Parcelable

class HealthCard(var title: String?, var description: String?, var image: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(title)
        dest?.writeString(title)
        dest?.writeInt(image)
    }

    override fun describeContents(): Int {
      return 0
    }

    companion object CREATOR : Parcelable.Creator<HealthCard> {
        override fun createFromParcel(parcel: Parcel): HealthCard {
            return HealthCard(parcel)
        }

        override fun newArray(size: Int): Array<HealthCard?> {
            return arrayOfNulls(size)
        }
    }
}

data class PreventionDetailCard(var title: String?, var description: String?, var image: Int)

data class VerticalRv(var title: String, var verticalItems: List<HealthCard>)

data class CountriesVerticalRv(var title: String, var countries: List<StatCountries>)

package com.devloyde.healthguard.db


import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.devloyde.healthguard.models.*
import okio.Timeout


@Dao
interface StatDao {
    @Insert(onConflict = REPLACE)
    fun saveGlobalStat(globalStat: GlobalStat)

    @Insert(onConflict = REPLACE)
    fun saveStatCountries(vararg countries: StatCountries)

    @Insert(onConflict = REPLACE)
    fun saveStatHistory(vararg history: StatHistory)

    @Query("SELECT * FROM globalstat")
    fun loadGlobalStat(): LiveData<GlobalStat>

    @Query("SELECT * FROM statcountries")
    fun loadCountriesStat(): LiveData<List<StatCountries>>

    @Query("SELECT * FROM statcountries WHERE statcountries.isoCode = :isoCode ")
    fun loadOneCountriesStat(isoCode: String): LiveData<StatCountries>

    @Query("SELECT * FROM stathistory")
    fun loadHistoryStat(): LiveData<List<StatHistory>>

    @Query("DELETE FROM globalstat")
    fun deleteGlobalStat()

    @Query("SELECT * FROM statcountries")
    fun deleteCountriesStat(): LiveData<List<CountryNews>>

    @Query("DELETE FROM stathistory")
    fun deleteHistoryStat()

    @Insert(onConflict = REPLACE)
    fun saveTimeout( timeout: TimeoutCheck)

    @Query("SELECT * FROM timeoutcheck WHERE id == :id")
    fun checkTimeout(id: Int) : TimeoutCheck?

    @Query("DELETE FROM timeoutcheck WHERE id == :id")
    fun deleteTimeout(id: Int)
}


@Dao
interface NewsDao {
    @Insert(onConflict = REPLACE)
     fun saveRecommendedNews(vararg news: RecommendedNews)

    @Insert(onConflict = REPLACE)
     fun saveLocalNews(vararg news: LocalNews)

    @Insert(onConflict = REPLACE)
     fun saveCountryNews(vararg news: CountryNews)

    @Insert(onConflict = REPLACE)
     fun saveGlobalNews(vararg news: GlobalNews)

    @Insert(onConflict = REPLACE)
    fun saveTimeout( timeout: TimeoutCheck)

    @Query("SELECT * FROM timeoutcheck WHERE id == :id")
    fun checkTimeout(id: Int) : TimeoutCheck?

    @Query("DELETE FROM timeoutcheck WHERE id == :id")
    fun deleteTimeout(id: Int)

    @Query("SELECT * FROM recommendednews")
     fun loadRecommendedNews(): LiveData<List<RecommendedNews>>

    @Query("DELETE FROM recommendednews")
    fun deleteRecommendedNews()

    @Query("SELECT * FROM localnews")
     fun loadLocalNews(): LiveData<List<LocalNews>>

    @Query("DELETE FROM localnews")
    fun deleteLocalNews()

    @Query("SELECT * FROM globalnews")
     fun loadGlobalNews(): LiveData<List<GlobalNews>>

    @Query("DELETE FROM globalnews")
    fun deleteGlobalNews()

    @Query("SELECT * FROM countrynews")
     fun loadCountryNews(): LiveData<List<CountryNews>>

    @Query("DELETE FROM countrynews")
    fun deleteCountryNews()

}

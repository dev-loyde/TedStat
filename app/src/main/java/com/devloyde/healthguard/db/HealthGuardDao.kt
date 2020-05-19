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
    fun saveStatCountries(countries: StatCountries)

    @Insert(onConflict = REPLACE)
    fun saveStatHistory(history: StatHistory)

    @Query("SELECT * FROM globalstat")
    fun loadGlobalStat(): LiveData<List<GlobalStat>>

    @Query("SELECT * FROM statcountries")
    fun loadCountriesStat(): LiveData<List<StatCountries>>

    @Query("SELECT * FROM statcountries WHERE statcountries.isoCode = :isoCode ")
    fun loadOneCountriesStat(isoCode: String): LiveData<StatCountries>

    @Query("SELECT * FROM stathistory")
    fun loadHistoryStat(): LiveData<List<StatHistory>>
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

    @Query("SELECT COUNT(*) FROM timeoutcheck WHERE name == :name AND timeout <= :currentTime")
    fun checkTimeout(currentTime:Long, name: String) : Int

    @Query("DELETE FROM timeoutcheck WHERE name == :name")
    fun deleteTimeout(name: String)

    @Query("SELECT * FROM recommendednews")
     fun loadRecommendedNews(): LiveData<List<RecommendedNews>>

    @Query("SELECT * FROM localnews")
     fun loadLocalNews(): LiveData<List<LocalNews>>

    @Query("SELECT * FROM globalnews")
     fun loadGlobalNews(): LiveData<List<GlobalNews>>

    @Query("SELECT * FROM countrynews")
     fun loadCountryNews(): LiveData<List<CountryNews>>

}

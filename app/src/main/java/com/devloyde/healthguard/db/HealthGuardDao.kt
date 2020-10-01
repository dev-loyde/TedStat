package com.devloyde.healthguard.db


import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.devloyde.healthguard.models.*

@Dao
interface StatDao {
    @Insert(onConflict = REPLACE)
    fun saveGlobalStat(globalStat: GlobalStat)

    @Insert(onConflict = REPLACE)
    fun saveStatCountries(vararg countries: StatCountries)

    @Query("SELECT * FROM globalstat")
    fun loadGlobalStat(): LiveData<GlobalStat>

    @Query("SELECT * FROM statcountries")
    fun loadCountriesStat(): LiveData<List<StatCountries>>

    @Query("SELECT * FROM statcountries WHERE country = :country ")
    fun loadOneCountriesStat(country: String): LiveData<StatCountries>

    @Query("DELETE FROM globalstat")
    fun deleteGlobalStat()

    @Query("SELECT * FROM statcountries")
    fun deleteCountriesStat(): LiveData<List<CountryNews>>

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

@Dao
interface InfoDao {
    @Insert(onConflict = REPLACE)
    fun saveAdvisory(vararg news: AdvisoryInfo)

    @Insert(onConflict = REPLACE)
    fun saveFaq(vararg news: FaqInfo)

    @Query("SELECT * FROM advisoryInfo")
    fun loadAdvisory(): LiveData<List<AdvisoryInfo>>

    @Query("DELETE FROM advisoryInfo")
    fun deleteAdvisory()

    @Query("SELECT * FROM faqInfo")
    fun loadFaq(): LiveData<List<FaqInfo>>

    @Query("DELETE FROM faqInfo")
    fun deleteFaq()

    @Insert(onConflict = REPLACE)
    fun saveTimeout( timeout: TimeoutCheck)

    @Query("SELECT * FROM timeoutcheck WHERE id == :id")
    fun checkTimeout(id: Int) : TimeoutCheck?

    @Query("DELETE FROM timeoutcheck WHERE id == :id")
    fun deleteTimeout(id: Int)
}

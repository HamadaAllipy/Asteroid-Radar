package com.example.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ImageOfDayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImageOfDay(picture: DatabaseImageOfDay)

    @Query(value = "SELECT * FROM DatabaseImageOfDay pod ORDER BY pod.date DESC LIMIT 0,1")
    fun getImageOfDay(): LiveData<DatabaseImageOfDay>

}

@Dao
interface AsteroidDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAsteroid(vararg asteroids: DatabaseAsteroid)


    @Query(value = "SELECT * FROM DatabaseAsteroid databaseAsteroid WHERE databaseAsteroid.closeApproachDate = :today")
    fun getTodayAsteroids(today: String) : LiveData<List<DatabaseAsteroid>>


    @Query(value = "SELECT * FROM DatabaseAsteroid databaseAsteroid WHERE databaseAsteroid.closeApproachDate BETWEEN :startDate  AND :endDate  order by closeApproachDate desc")
    fun getWeeklyAsteroids(startDate: String, endDate: String) : LiveData<List<DatabaseAsteroid>>


    @Query(value = "SELECT * FROM DatabaseAsteroid ORDER BY closeApproachDate DESC")
    fun getAllAsteroids() : LiveData<List<DatabaseAsteroid>>



}

@Database(entities = [DatabaseImageOfDay::class, DatabaseAsteroid::class], version = 1, exportSchema = false)
abstract class NasaDatabase : RoomDatabase() {
    abstract val imageOfDayDao: ImageOfDayDao
    abstract val asteroidDao: AsteroidDao
}

private lateinit var INSTANCE: NasaDatabase

fun getDatabase(context: Context): NasaDatabase {
    synchronized(NasaDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                NasaDatabase::class.java, "NasaDatabase").build()
        }
    }
    return INSTANCE
}
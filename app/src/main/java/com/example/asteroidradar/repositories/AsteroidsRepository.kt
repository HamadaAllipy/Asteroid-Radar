package com.example.asteroidradar.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.asteroidradar.Utils
import com.example.asteroidradar.api.parseAsteroidsJsonResult
import com.example.asteroidradar.database.NasaDatabase
import com.example.asteroidradar.database.asDomainModel
import com.example.asteroidradar.domain.Asteroid
import com.example.asteroidradar.network.AsteroidApi
import com.example.asteroidradar.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import timber.log.Timber
import java.util.*


class AsteroidsRepository(private val database:NasaDatabase) {
    private val _today = Utils.formatDate(Calendar.getInstance().time)
    private val _week = Utils.formatDate(Utils.addAWeekToDate(Calendar.getInstance().time))


    val todayAsteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getTodayAsteroids(_today)) {
            it.asDomainModel()
        }


    val allAsteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAllAsteroids()) {
            it.asDomainModel()
        }

    val weekAsteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getWeeklyAsteroids(_today,_week)) {
            it.asDomainModel()
        }



    suspend fun updateAsteroids() {


        withContext(Dispatchers.IO) {
            try {
                val asteroids = AsteroidApi.retrofitService.getAsteroidsAsync("2sicsShpsuz2duLcnMjLsfd2I5EZlnNtB7Nvayo3").await()
                database.asteroidDao.insertAllAsteroid(*parseAsteroidsJsonResult(JSONObject(asteroids)).asDatabaseModel())
            } catch (e: Exception) {
                Timber.e("Error ${e.message}")
            }
        }
    }
}
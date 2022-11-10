package com.example.asteroidradar.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.asteroidradar.database.NasaDatabase
import com.example.asteroidradar.database.asDomainModel
import com.example.asteroidradar.domain.ImageOfDay
import com.example.asteroidradar.network.AsteroidApi
import com.example.asteroidradar.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await
import timber.log.Timber


class ImagesOfDayRepository(private val database: NasaDatabase) {

    val imageOfDay: LiveData<ImageOfDay> =
        Transformations.map(database.imageOfDayDao.getImageOfDay()) {
            it?.asDomainModel()
        }

     suspend fun updateImageOfDay() {
        withContext(Dispatchers.IO) {
            try {
                val imageOfDay =
                    AsteroidApi.retrofitService.getImageOfDay("2sicsShpsuz2duLcnMjLsfd2I5EZlnNtB7Nvayo3").await()
                database.imageOfDayDao.insertImageOfDay(imageOfDay.asDatabaseModel())
            } catch (e: Exception) {
                Timber.e("Error ${e.message}")
            }
        }
    }
}
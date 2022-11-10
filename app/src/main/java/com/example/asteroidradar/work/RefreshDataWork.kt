package com.example.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.asteroidradar.database.getDatabase
import com.example.asteroidradar.repositories.AsteroidsRepository
import com.example.asteroidradar.repositories.ImagesOfDayRepository
import retrofit2.HttpException


class RefreshDataWorker(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val pictureOfDayRepository = ImagesOfDayRepository(database)
        val asteroidsRepository = AsteroidsRepository(database)
        return try {
            pictureOfDayRepository.updateImageOfDay()
            asteroidsRepository.updateAsteroids()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}
package com.example.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.example.asteroidradar.database.getDatabase
import com.example.asteroidradar.domain.Asteroid
import com.example.asteroidradar.network.AsteroidApiFilter
import com.example.asteroidradar.repositories.AsteroidsRepository
import com.example.asteroidradar.repositories.ImagesOfDayRepository
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val filter = MutableLiveData(AsteroidApiFilter.SHOW_SAVED)
    private val imagesOfDayRepository = ImagesOfDayRepository(database)
    private val asteroidsRepository = AsteroidsRepository(database)

    init {
        viewModelScope.launch {
            imagesOfDayRepository.updateImageOfDay()
            asteroidsRepository.updateAsteroids()
        }
    }

    val picOfDay = imagesOfDayRepository.imageOfDay
    val asteroids = Transformations.switchMap(filter){
        when (it) {
            AsteroidApiFilter.SHOW_TODAY -> asteroidsRepository.todayAsteroids
            AsteroidApiFilter.SHOW_WEEK -> asteroidsRepository.weekAsteroids
            else -> asteroidsRepository.allAsteroids
        }
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    val navigateToSelected: MutableLiveData<Asteroid?>
        get() = _navigateToSelected

    private val _navigateToSelected = MutableLiveData<Asteroid?>()


    fun displayAsteroidDetails() {
        _navigateToSelected.value = null
    }

    fun asteroidDetail(asteroid: Asteroid) {
        _navigateToSelected.value = asteroid
    }

    fun updateFilter(filter: AsteroidApiFilter) {
        this.filter.value = filter
    }

}
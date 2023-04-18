package hu.bme.aut.android.fruitcatalog.ui.home

import androidx.annotation.WorkerThread
import hu.bme.aut.android.fruitcatalog.network.FruitService
import hu.bme.aut.android.fruitcatalog.persistence.FruitDao
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val fruitService: FruitService,
    private val fruitDao: FruitDao
) {
}
package hu.bme.aut.android.fruitcatalog.ui.details

import androidx.annotation.WorkerThread
import hu.bme.aut.android.fruitcatalog.network.FruitService
import hu.bme.aut.android.fruitcatalog.persistence.FruitDao
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val fruitDao: FruitDao
) {
}
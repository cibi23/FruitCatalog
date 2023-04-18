package hu.bme.aut.android.fruitcatalog.ui.details

import androidx.annotation.WorkerThread
import javax.inject.Inject

class DetailsRepository @Inject constructor() {
    @WorkerThread
    fun getFruitById(id: Long){}
}
package hu.bme.aut.android.fruitcatalog.ui.details

import androidx.annotation.WorkerThread
import hu.bme.aut.android.fruitcatalog.network.FruitService
import hu.bme.aut.android.fruitcatalog.persistence.FruitDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val fruitDao: FruitDao
) {
    @WorkerThread
    fun getFruitById(id: Long) = flow {
        val poster = fruitDao.getFruitById(id)
        emit(poster)
    }.flowOn(Dispatchers.IO)
}
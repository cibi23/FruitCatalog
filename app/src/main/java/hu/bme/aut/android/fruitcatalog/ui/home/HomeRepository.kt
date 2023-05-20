package hu.bme.aut.android.fruitcatalog.ui.home

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.suspendOnSuccess
import hu.bme.aut.android.fruitcatalog.model.Fruit
import hu.bme.aut.android.fruitcatalog.network.FruitService
import hu.bme.aut.android.fruitcatalog.persistence.FruitDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val fruitService: FruitService,
    private val fruitDao: FruitDao
) {
    @WorkerThread
    fun loadFruits(
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        onError: (String) -> Unit
    ) = flow {
        val fruits: List<Fruit> = fruitDao.getAllFruits()
        if (fruits.isEmpty()) {
            fruitService.getAllFruits()
                .suspendOnSuccess {
                    fruitDao.insertFruits(data)
                    emit(data)
                }
                .onFailure { onError(message()) }
        } else {
            emit(fruits)
        }
    }.onStart { onStart() }.onCompletion { onCompletion() }.flowOn(Dispatchers.IO)

    @WorkerThread
    suspend fun updateFruit(
        fruit: Fruit
    ) = fruitDao.updateFruit(fruit)


    @WorkerThread
    suspend fun insertFruit(
        fruit: Fruit
    ) {
        val list: List<Fruit> = listOf(fruit)
        fruitDao.insertFruits(list)
    }
    @WorkerThread
    suspend fun deleteFruit(
        fruit: Fruit
    ) = fruitDao.deleteFruit(fruit)





}
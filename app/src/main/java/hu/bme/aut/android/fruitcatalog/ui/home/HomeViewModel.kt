package hu.bme.aut.android.fruitcatalog.ui.home

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.fruitcatalog.model.Fruit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel(){
    val fruitList: Flow<List<Fruit>> =
        homeRepository.loadFruits(
            onStart = { },
            onCompletion = { },
            onError = {}
        )

    suspend fun deleteFruit(fruit: Fruit) = homeRepository.deleteFruit(fruit)
    suspend fun insertFruit(fruit: Fruit) = homeRepository.insertFruit(fruit)
    suspend fun updateFruit(fruit: Fruit) = homeRepository.updateFruit(fruit)
}
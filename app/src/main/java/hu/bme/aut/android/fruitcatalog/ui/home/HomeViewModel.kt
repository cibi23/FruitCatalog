package hu.bme.aut.android.fruitcatalog.ui.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.fruitcatalog.model.Fruit
import kotlinx.coroutines.flow.Flow
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
}
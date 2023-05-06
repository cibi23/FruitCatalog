package hu.bme.aut.android.fruitcatalog.ui.details

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailRepository: DetailsRepository
) : ViewModel() {
    private val fruitIdSharedFlow: MutableSharedFlow<Long> = MutableSharedFlow(replay = 1)
    val fruitFlow = fruitIdSharedFlow.flatMapLatest {
        detailRepository.getFruitById(it)
    }
    fun loadFruitById(id: Long) = fruitIdSharedFlow.tryEmit(id)
}
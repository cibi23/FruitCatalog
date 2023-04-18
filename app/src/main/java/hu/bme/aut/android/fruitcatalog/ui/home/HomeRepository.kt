package hu.bme.aut.android.fruitcatalog.ui.home

import androidx.annotation.WorkerThread
import javax.inject.Inject

class HomeRepository @Inject constructor(
    //
) {
    @WorkerThread
    fun loadFruits(
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        onError: (String) -> Unit
    ) {
    }
}
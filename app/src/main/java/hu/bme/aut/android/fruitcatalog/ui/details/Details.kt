package hu.bme.aut.android.fruitcatalog.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import hu.bme.aut.android.fruitcatalog.model.Fruit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight

import androidx.compose.foundation.layout.padding



@Composable
fun FruitDetails(
    fruitId: Long,
    viewModel: DetailsViewModel,
    pressOnBack: () -> Unit = {}
) {
    LaunchedEffect(key1 = fruitId) {
        viewModel.loadFruitById(fruitId)
    }

    val details: Fruit? by viewModel.fruitFlow.collectAsState(initial = null)
    details?.let { fruit ->
        FruitDetailsBody(fruit, pressOnBack)
    }
}

@Composable
private fun FruitDetailsBody(
    fruit: Fruit,
    pressOnBack: () -> Unit
) {
    Column(){
        Text(
            text="Family: " + fruit.family,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
        )
        Text(
            text="Genus: " + fruit.genus,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
        )
        Text(
            text="Sugar: " + fruit.nutritions.sugar,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
        )
        Text(
            text="Carbohydrates: " + fruit.nutritions.carbohydrate,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
        )
        Text(
            text="Protein: " + fruit.nutritions.protein,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
        )
        Text(
            text="Fat: " + fruit.nutritions.fat,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
        )
        Text(
            text="Calories: " + fruit.nutritions.calories,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
        )
    }
}

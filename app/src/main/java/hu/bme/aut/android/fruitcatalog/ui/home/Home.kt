package hu.bme.aut.android.fruitcatalog.ui.home

import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import hu.bme.aut.android.fruitcatalog.model.Fruit
import hu.bme.aut.android.fruitcatalog.ui.details.FruitDetails
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight.Companion.Black
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.window.Popup
import hu.bme.aut.android.fruitcatalog.model.Nutritions
import kotlinx.coroutines.launch

@Composable
fun FruitCatalogHomeScreen() {
    val navController = rememberNavController()
    var title by remember { mutableStateOf("Fruit Catalog")}
    NavHost(navController = navController, startDestination = NavScreen.Home.route) {
        composable(NavScreen.Home.route) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        modifier = Modifier.padding(0.dp, 18.dp, 0.dp, 0.dp),
                        title = { Text(text = "Fruit Catalog") }

                    )
                },
                content = {
                    Fruits(
                        viewModel = hiltViewModel(),
                        selectFruit = {
                            navController.navigate("${NavScreen.FruitDetails.route}/$it")
                        },
                        setTitle = {
                            title = it
                        }
                    )
                })

        }
        composable(
            route = NavScreen.FruitDetails.routeWithArgument,
            arguments = listOf(
                navArgument(NavScreen.FruitDetails.argument0) { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val fruitId =
                backStackEntry.arguments?.getLong(NavScreen.FruitDetails.argument0) ?: return@composable
            Scaffold(
                topBar = {
                    TopAppBar(
                        modifier = Modifier.padding(0.dp, 18.dp, 0.dp, 0.dp),
                        title = { Text(text = title) },
                        navigationIcon =
                            {
                                IconButton(onClick = { navController.navigateUp() }) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowBack,
                                        contentDescription = "Back"
                                    )
                                }
                        }

                    )
                },
                content = {
                    FruitDetails(fruitId = fruitId, viewModel = hiltViewModel()) {
                        navController.navigateUp()
                    }
                })

        }
    }
}

sealed class NavScreen(val route: String) {

    object Home : NavScreen("Home")
    object FruitDetails : NavScreen("FruitDetails") {
        const val routeWithArgument: String = "FruitDetails/{fruitId}"
        const val argument0: String = "fruitId"
    }
}

@Composable
fun Fruits(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    selectFruit: (Long) -> Unit,
    setTitle: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val fruits: List<Fruit> by viewModel.fruitList.collectAsState(initial = listOf())
    var searchText by remember { mutableStateOf("") }
    var showPopUp by remember { mutableStateOf(false) }
    if (showPopUp) {
        val nut =
            Nutritions(calories = 0.0, carbohydrate = 0.0, fat = 0.0, sugar = 0.0, protein = 0.0)
        NewFruit(
            fruit = Fruit(
                id = -1,
                name = "",
                family = "",
                genus = "",
                nutritions = nut,
                order = ""
            ), doneCallback = {
                coroutineScope.launch {
                    viewModel.insertFruit(it)
                }
                              }, setShowPopUp = { showPopUp = it })
    } else {
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colors.background)
                .padding(10.dp)
        ) {
            Row(modifier = modifier.padding(0.dp, 10.dp)) {
                TextField(value = searchText, onValueChange = { value ->
                    searchText = value
                }, placeholder = { Text("Search") })
                IconButton(onClick = {
                }) {
                    Icon(
                        Icons.Filled.Search,
                        "contentDescription",
                    )
                }
                Spacer(modifier = modifier.weight(1f))
                IconButton(onClick = {
                    showPopUp = true
                }) {
                    Icon(
                        Icons.Filled.Add,
                        "contentDescription",
                    )
                }
            }
            fruits.forEach { fruit ->
                if (searchText == "" || fruit.name.toLowerCase().contains(searchText.toLowerCase()))
                    MyFruit(
                        modifier = modifier,
                        fruit = fruit,
                        selectFruit = selectFruit,
                        setTitle = setTitle,
                        deleteFruit = {
                            coroutineScope.launch {
                                viewModel.deleteFruit(it)
                            }
                        },
                        updateFruit = {
                            coroutineScope.launch {
                                viewModel.updateFruit(it)
                            }
                        }
                    )
            }
        }
    }
}

@Composable
private fun MyFruit(
    modifier: Modifier = Modifier,
    fruit: Fruit,
    selectFruit: (Long) -> Unit = {},
    setTitle: (String) -> Unit,
    deleteFruit: (Fruit) -> Unit,
    updateFruit: (Fruit) -> Unit

) {
    var showPopUp by remember { mutableStateOf(false)}
    if(showPopUp){
        NewFruit(fruit = fruit, doneCallback = {
              updateFruit(it)
        }, setShowPopUp = {showPopUp = it})
    }
    Surface(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable(
                onClick = {
                    selectFruit(fruit.id)
                    setTitle(fruit.name)
                }
            ),
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding(0.dp, 10.dp)
        ){
            Text(
                modifier = modifier
                    .padding(8.dp, 0.dp),
                text = fruit.name,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier.weight(0.5f))
            Column(){
                Text(
                    text="Family: " + fruit.family,
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text="Sugar: " + fruit.nutritions.sugar,
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                )
            }
            Spacer(modifier.weight(1f))
            IconButton(onClick = {
                showPopUp = !showPopUp
            }) {
                Icon(
                    Icons.Filled.Edit,
                    "contentDescription",
                )
            }
            IconButton(onClick = {
                deleteFruit(fruit)
            }) {
                Icon(
                    Icons.Filled.Delete,
                    "contentDescription",
                )
            }
        }
    }
}

@Composable
fun NewFruit(
    modifier: Modifier = Modifier,
    fruit: Fruit,
    doneCallback: (Fruit) -> Unit,
    setShowPopUp: (Boolean) -> Unit
){
    var name by remember { mutableStateOf(fruit.name)}
    var family by remember { mutableStateOf(fruit.family)}
    var genus by remember { mutableStateOf(fruit.genus)}
    var fat by remember { mutableStateOf(fruit.nutritions.fat)}
    var sugar by remember { mutableStateOf(fruit.nutritions.sugar)}
    var protein by remember { mutableStateOf(fruit.nutritions.protein)}
    var carbo by remember { mutableStateOf(fruit.nutritions.carbohydrate)}
    var calories by remember { mutableStateOf(fruit.nutritions.calories)}

        Surface(
            modifier = modifier
                .padding(16.dp)
                .background(Color.Transparent)
                .fillMaxHeight()
                .border(2.dp, MaterialTheme.colors.onSurface)
        ) {

        Column(
            modifier = modifier.padding(16.dp)
        ){
            Row(modifier = modifier.padding(4.dp)) {
                Text(
                    text="Name: ",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier.weight(1f))
                TextField(value = name, onValueChange = {value -> name = value})
            }
            Row(modifier = modifier.padding(4.dp)) {
                Text(
                    text="Family: ",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier.weight(1f))

                TextField(value = family, onValueChange = {value -> family = value})

            }
            Row(modifier = modifier.padding(4.dp)) {
                Text(
                    text="Genus: ",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier.weight(1f))

                TextField(value = genus, onValueChange = {value -> genus = value})

            }
            Row(modifier = modifier.padding(4.dp)) {
                Text(
                    text="Sugar: ",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier.weight(1f))

                TextField(value = sugar.toString(), onValueChange = {value -> sugar = value.toDouble()})

            }
            Row(modifier = modifier.padding(4.dp)) {
                Text(
                    text="Carbohydrates: ",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier.weight(1f))
                TextField(value = carbo.toString(), onValueChange = {value -> carbo = value.toDouble()})

            }
            Row(modifier = modifier.padding(4.dp)) {
                Text(
                    text="Protein: ",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier.weight(1f))
                TextField(value = protein.toString(), onValueChange = {value -> protein = value.toDouble()})

            }
            Row(modifier = modifier.padding(4.dp)) {
                Text(
                    text="Fat: ",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier.weight(1f))
                TextField(value = fat.toString(), onValueChange = {value -> fat = value.toDouble()})

            }
            Row(modifier = modifier.padding(4.dp)) {
                Text(
                    text="Calories: ",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier.weight(1f))
                TextField(value = calories.toString(), onValueChange = {value -> calories = value.toDouble()})

            }
            Row(modifier = modifier.padding(4.dp)){
                Spacer(modifier.weight(0.33f))
                Button(onClick = {
                    val nut =
                        Nutritions(
                            calories = calories,
                            carbohydrate = carbo,
                            fat = fat,
                            sugar = sugar,
                            protein = protein)
                    val resFruit = Fruit(
                            id = fruit.id,
                            name = name,
                            family = family,
                            genus = genus,
                            nutritions = nut,
                            order = "")
                    doneCallback(resFruit)
                    setShowPopUp(false)
                }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue) ) {
                    Text("Save", color = MaterialTheme.colors.onPrimary)
                }
                Spacer(modifier.weight(0.66f))
                Button(onClick = { setShowPopUp(false) }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray) ) {
                    Text("Back")
                }
            }
        }
    }
}

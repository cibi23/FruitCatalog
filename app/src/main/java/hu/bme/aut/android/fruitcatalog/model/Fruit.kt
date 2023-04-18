package hu.bme.aut.android.fruitcatalog.model

data class Fruit (
    val name: String,
    val family: String,
    val genus: String,
    val sugar: Double,
    val carbohydrate: Double,
    val protein: Double,
    val fat: Double,
    val calories: Double
)
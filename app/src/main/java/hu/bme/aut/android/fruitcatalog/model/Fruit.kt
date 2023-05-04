package hu.bme.aut.android.fruitcatalog.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fruits")
data class Fruit (
    @PrimaryKey val id: Int,
    val name: String,
    val family: String,
    val order: String,
    val genus: String,
    @Embedded val nutritions: Nutritions
) {
    companion object {

        fun mock() = Fruit(
            id = 3,
            name = "Strawberry",
            family = "Rosaceae",
            order = "Rosales",
            genus = "Fragaria",
            nutritions = Nutritions.mock()
        )
    }
}

data class Nutritions (
    val sugar: Double,
    val carbohydrate: Double,
    val protein: Double,
    val fat: Double,
    val calories: Double
) {

    companion object {

        fun mock() = Nutritions(
            sugar = 5.4,
            carbohydrate = 5.5,
            protein = 0.8,
            fat = 0.4,
            calories = 29.0
        )
    }
}
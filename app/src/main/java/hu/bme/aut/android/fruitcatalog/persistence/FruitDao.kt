package hu.bme.aut.android.fruitcatalog.persistence

import androidx.room.*
import hu.bme.aut.android.fruitcatalog.model.Fruit

@Dao
interface FruitDao {
    @Insert
    suspend fun insertFruits(fruits: List<Fruit>)

    @Update
    suspend fun updateFruit(fruit: Fruit)

    @Delete
    suspend fun deleteFruit(fruit: Fruit)

    @Query("SELECT * FROM fruits WHERE id = :id")
    fun getFruitById(id: Long): Fruit?

    @Query("SELECT * FROM fruits")
    fun getAllFruits(): List<Fruit>
}
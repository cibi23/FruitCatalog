package hu.bme.aut.android.fruitcatalog.persistence

import androidx.room.*
import hu.bme.aut.android.fruitcatalog.model.Fruit

@Dao
interface FruitDao {
    @Insert
    fun insertFruit(fruit: Fruit)

    @Update
    fun updateFruit(fruit: Fruit)

    @Delete
    fun deleteFruit(fruit: Fruit)

    @Query("SELECT * FROM fruits WHERE id = :id")
    fun getFruitById(id: Long): Fruit?

    @Query("SELECT * FROM fruits")
    suspend fun getAllFruits(): List<Fruit>
}
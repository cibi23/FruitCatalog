package hu.bme.aut.android.fruitcatalog.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.bme.aut.android.fruitcatalog.model.Fruit

@Database(entities = [Fruit::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun fruitDao(): FruitDao
}
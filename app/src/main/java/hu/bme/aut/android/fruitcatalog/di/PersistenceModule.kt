package hu.bme.aut.android.fruitcatalog.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.fruitcatalog.persistence.AppDatabase
import hu.bme.aut.android.fruitcatalog.persistence.FruitDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {
    @Provides
    @Singleton
    fun provideFruitDao(): FruitDao {
         return FruitDao()
    }
}
package hu.bme.aut.android.fruitcatalog.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.fruitcatalog.R
import hu.bme.aut.android.fruitcatalog.persistence.AppDatabase
import hu.bme.aut.android.fruitcatalog.persistence.FruitDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {
    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room
            .databaseBuilder(
                application,
                AppDatabase::class.java,
                application.getString(R.string.database)
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideFruitDao(appDatabase: AppDatabase): FruitDao {
         return appDatabase.fruitDao()
    }
}
package hu.bme.aut.android.fruitcatalog.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.fruitcatalog.network.FruitService
import hu.bme.aut.android.fruitcatalog.persistence.FruitDao
import hu.bme.aut.android.fruitcatalog.ui.home.HomeRepository

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideHomeRepository(
        fruitService: FruitService,
        fruitDao: FruitDao
    ): HomeRepository {
        return HomeRepository(fruitService, fruitDao)
    }
}
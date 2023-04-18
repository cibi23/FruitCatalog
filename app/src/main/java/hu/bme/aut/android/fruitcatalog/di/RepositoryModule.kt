package hu.bme.aut.android.fruitcatalog.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.fruitcatalog.ui.home.HomeRepository

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideMainRepository(
    ): HomeRepository {
        return HomeRepository()
    }
}
package hu.bme.aut.android.fruitcatalog.persistence

import androidx.room.Room
import org.junit.After
import org.junit.Before
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
abstract class TestDatabase {
    lateinit var db: AppDatabase
    @Before
    fun initDB() {
        db = Room.inMemoryDatabaseBuilder(getApplicationContext(), AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDB() {
        db.close()
    }
}
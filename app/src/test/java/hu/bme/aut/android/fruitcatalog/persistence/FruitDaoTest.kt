package hu.bme.aut.android.fruitcatalog.persistence

import hu.bme.aut.android.fruitcatalog.model.Fruit
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.hamcrest.core.Is.`is`
import kotlinx.coroutines.test.runTest
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [23])
class FruitDaoTest : TestDatabase() {
    private lateinit var fruitDao: FruitDao

    @Before
    fun init() {
        fruitDao = db.fruitDao()
    }

    @Test
    fun insertAndLoadFruitListTest() = runTest {
        val mockDataList = listOf(Fruit.mock())
        fruitDao.insertFruits(mockDataList)

        val loadFromDB = fruitDao.getAllFruits()
        MatcherAssert.assertThat(loadFromDB.toString(), `is`(mockDataList.toString()))
    }

    @Test
    fun loadFruitById() = runTest {
        val mockDataList = listOf(Fruit.mock())
        fruitDao.insertFruits(mockDataList)

        val loadedFromDB = fruitDao.getFruitById(3)
        val mockData = Fruit.mock()
        MatcherAssert.assertThat(loadedFromDB.toString(), `is`(mockData.toString()))
    }

    @Test
    fun updateFruitTest() = runTest {
        val mockDataList = listOf(Fruit.mock())
        fruitDao.insertFruits(mockDataList)

        var loadFromDB = fruitDao.getFruitById(3)
        val mockData = Fruit.mock()
        loadFromDB = mockData
        fruitDao.updateFruit(loadFromDB)
        MatcherAssert.assertThat(loadFromDB.toString(), `is`(mockData.toString()))
    }

    @Test
    fun deleteFruitTest() = runTest {
        val mockDataList = listOf(Fruit.mock())
        fruitDao.insertFruits(mockDataList)
        val loadFromDB = fruitDao.getFruitById(3)
        loadFromDB?.let { fruitDao.deleteFruit(it) }
        val deleted = fruitDao.getFruitById(3)
        assert(deleted == null)
    }
}
package com.practice.movidb.shared.data.db


import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var searchDao: SearchFtsDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        searchDao = db.searchFtsDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndGetMovie() {
        val movie = MovieEntity(
            rowId = 0,
            adult = false,
            genreIds = listOf(
                10749,
                18,
                35
            ),
            id = 35549,
            title = "Les Galettes de Pont-Aven",
            overview = "Henri Serin, a sales rep in umbrellas who lives in Saumur, is bored with his life. Married to an unloving, uptight wife, who, unlike him, has no interest in sex, he is also rejected by his children. Thus, he spends most of his time travelling from town to town, flirting with women and meeting all kinds of people in the hotels, restaurants, shops and places he visits. He also dabbles in painting, particularly portraits. After his car breaks down as he is driving through Brittany, he meets a rough, foul-mouthed painter who offers him to stay at his house, near Pont-Aven. There, Henri falls in love with Angela, the painters model and fling, and soon they run off together...",
            posterPath = "/hMqoGJtvuKOFO3Lou7cq0keawDW.jpg",
            releaseDate = "1975-08-20",
            voteAverage = 6.4,
            voteCount = 51
        )
        searchDao.insertAll(listOf(movie))

        val movieList = searchDao.getSearchList()

        assertEquals(movieList, listOf(movie))
    }
}
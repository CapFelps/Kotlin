package br.com.fiap.softekfiap.data

import androidx.room.*
import br.com.fiap.softekfiap.model.EmotionEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface EmotionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: EmotionEntry)

    @Query("SELECT * FROM check_in ORDER BY date DESC")
    fun getAll(): Flow<List<EmotionEntry>>

    @Query("SELECT * FROM check_in WHERE date = :date")
    suspend fun getByDate(date: String): EmotionEntry?

    @Delete
    suspend fun delete(entry: EmotionEntry)

    @Query("SELECT * FROM check_in WHERE userId = :userId ORDER BY date DESC")
    fun getAllByUser(userId: Int?): Flow<List<EmotionEntry>>

    @Query("SELECT COUNT(*) FROM check_in WHERE userId = :userId AND date = :date")
    suspend fun verificaCheckIn(userId: Int?, date: String): Int
}

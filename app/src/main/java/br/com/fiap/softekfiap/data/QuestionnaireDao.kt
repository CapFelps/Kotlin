package br.com.fiap.softekfiap.data

import androidx.room.*
import br.com.fiap.softekfiap.model.QuestionnaireEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionnaireDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: QuestionnaireEntry)

    @Query("SELECT * FROM questionnaire_entries WHERE userId = :userId ORDER BY date DESC")
    fun getAllByUser(userId: Int?): Flow<List<QuestionnaireEntry>>

    @Query("SELECT * FROM questionnaire_entries ORDER BY date DESC")
    fun getAll(): Flow<List<QuestionnaireEntry>>

    @Query("""
    SELECT * FROM questionnaire_entries
    WHERE userId = :userId 
      AND strftime('%Y-%m', date) = strftime('%Y-%m', :currentDate)
    ORDER BY date DESC
""")
    fun getEntriesThisMonth(userId: Int?, currentDate: String): List<QuestionnaireEntry>


}

package br.com.fiap.softekfiap.repository

import br.com.fiap.softekfiap.data.EmotionDao
import br.com.fiap.softekfiap.model.EmotionEntry
import kotlinx.coroutines.flow.Flow

class EmotionRepository(private val dao: EmotionDao) {

    suspend fun insertEmotion(entry: EmotionEntry) {
        dao.insert(entry)
    }

    fun getAllEmotions(): Flow<List<EmotionEntry>> = dao.getAll()

    suspend fun getEmotionByDate(date: String): EmotionEntry? = dao.getByDate(date)

    suspend fun deleteEmotion(entry: EmotionEntry) {
        dao.delete(entry)
    }

    fun getAllByUser(userId: Int?): Flow<List<EmotionEntry>> = dao.getAllByUser(userId)
}

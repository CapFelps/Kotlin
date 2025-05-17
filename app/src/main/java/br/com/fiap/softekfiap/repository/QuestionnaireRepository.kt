package br.com.fiap.softekfiap.repository

import br.com.fiap.softekfiap.data.QuestionnaireDao
import br.com.fiap.softekfiap.model.QuestionnaireEntry
import kotlinx.coroutines.flow.Flow

class QuestionnaireRepository(private val dao: QuestionnaireDao) {
    suspend fun insert(entry: QuestionnaireEntry) = dao.insert(entry)
    fun getAllByUser(userId: Int?): Flow<List<QuestionnaireEntry>> = dao.getAllByUser(userId)
    fun getAll(): Flow<List<QuestionnaireEntry>> = dao.getAll()
}

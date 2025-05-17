package br.com.fiap.softekfiap.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.fiap.softekfiap.model.EmotionEntry
import br.com.fiap.softekfiap.repository.EmotionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class EmotionViewModel(private val repository: EmotionRepository) : ViewModel() {

    // Use todos os check-ins (não filtrados por usuário)
    val allEmotions = repository.getAllEmotions()
        .map { it.sortedByDescending { entry -> entry.date } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Use check-ins filtrados por userId (opcional)
    fun emotionsByUser(userId: Int?) = repository.getAllByUser(userId)

    fun insertEmotion(entry: EmotionEntry) {
        viewModelScope.launch {
            repository.insertEmotion(entry)
        }
    }

    fun deleteEmotion(entry: EmotionEntry) {
        viewModelScope.launch {
            repository.deleteEmotion(entry)
        }
    }
}

class EmotionViewModelFactory(private val repository: EmotionRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EmotionViewModel(repository) as T
    }
}

package br.com.fiap.softekfiap.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.fiap.softekfiap.model.QuestionnaireEntry
import br.com.fiap.softekfiap.repository.QuestionnaireRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuestionnaireEntryViewModel(private val repository: QuestionnaireRepository) : ViewModel() {
    private val _saveSuccess = MutableStateFlow(false)
    val saveSuccess: StateFlow<Boolean> = _saveSuccess

    fun saveQuestionnaire(userId: Int?, respostasJson: String, date: String) {
        viewModelScope.launch {
            val entry = QuestionnaireEntry(
                userId = userId,
                respostasJson = respostasJson,
                date = date
            )
            repository.insert(entry)
            _saveSuccess.value = true
        }
    }
}

class QuestionnaireEntryViewModelFactory(private val repository: QuestionnaireRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuestionnaireEntryViewModel(repository) as T
    }
}

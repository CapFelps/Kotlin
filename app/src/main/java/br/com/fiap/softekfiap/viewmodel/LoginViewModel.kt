package br.com.fiap.softekfiap.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.softekfiap.model.User
import br.com.fiap.softekfiap.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    private val _loginResult = MutableStateFlow<Result<User>?>(null)
    val loginResult: StateFlow<Result<User>?> = _loginResult

    private val _registerResult = MutableStateFlow<Result<Unit>?>(null)
    val registerResult: StateFlow<Result<Unit>?> = _registerResult

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val user = repository.login(username, password)
                if (user != null) {
                    _loginResult.value = Result.success(user)
                } else {
                    _loginResult.value = Result.failure(Exception("Credenciais inválidas"))
                }
            } catch (e: Exception) {
                _loginResult.value = Result.failure(e)
            }
        }
    }

    fun register(username: String, password: String) {
        viewModelScope.launch {
            try {
                val exists = repository.isUsernameTaken(username)
                if (exists) {
                    _registerResult.value = Result.failure(Exception("Nome de usuário já existe"))
                } else {
                    val createdAt = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
                    val user = User(username = username, password = password, createdAt = createdAt)
                    repository.register(user)
                    _registerResult.value = Result.success(Unit)
                }
            } catch (e: Exception) {
                _registerResult.value = Result.failure(e)
            }
        }
    }

    fun clearResults() {
        _loginResult.value = null
        _registerResult.value = null
    }
}

package br.com.fiap.softekfiap.repository

import br.com.fiap.softekfiap.data.UserDao
import br.com.fiap.softekfiap.model.User

class UserRepository(private val userDao: UserDao) {
    suspend fun register(user: User) = userDao.register(user)
    suspend fun login(username: String, password: String): User? = userDao.login(username, password)
    suspend fun isUsernameTaken(username: String): Boolean = userDao.getByUsername(username) != null
}

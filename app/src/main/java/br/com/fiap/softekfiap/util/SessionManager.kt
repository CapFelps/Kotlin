package br.com.fiap.softekfiap.util

import android.content.Context
import android.content.SharedPreferences

object SessionManager {
    private const val PREF_NAME = "softek_prefs"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_USERNAME = "user_id"

    fun salvarUsuario(context: Context, userId: Int, username: String) {
        val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        prefs.edit()
            .putInt("userId", userId)
            .putString("nomeUsuario", username)
            .apply()
    }

    fun getIdUsuario(context: Context): Int? {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val id = prefs.getInt(KEY_USER_ID, -1)
        return if (id != -1) id else null
    }

    fun getUsername(context: Context): String? {
        val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        return prefs.getString("nomeUsuario", null)
    }

    fun limparSessao(context: Context) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }
}

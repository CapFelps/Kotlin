package br.com.fiap.softekfiap.util

import android.content.Context
import java.util.*

object AnonymousIdManager {

    private const val PREFS_NAME = "anonymous_prefs"
    private const val KEY_ANONYMOUS_ID = "anonymous_id"

    fun getAnonymousId(context: Context): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        var id = prefs.getString(KEY_ANONYMOUS_ID, null)

        if (id == null) {
            id = UUID.randomUUID().toString()
            prefs.edit().putString(KEY_ANONYMOUS_ID, id).apply()
        }

        return id
    }
}

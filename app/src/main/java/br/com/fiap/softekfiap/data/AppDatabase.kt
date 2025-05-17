package br.com.fiap.softekfiap.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.fiap.softekfiap.model.EmotionEntry
import br.com.fiap.softekfiap.model.QuestionnaireEntry
import br.com.fiap.softekfiap.model.User

@Database(entities = [EmotionEntry::class, User::class, QuestionnaireEntry::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun emotionDao(): EmotionDao
    abstract fun userDao(): UserDao
    abstract fun questionnaireDao(): QuestionnaireDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "emotion_db"
                ).fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
    }
}

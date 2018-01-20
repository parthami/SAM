package parth.mfa_fingerprint.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

/**
 * Created by Parth Chandratreya on 13/01/2018.
 */

@Database(entities = arrayOf(AuthenticationNodeLog::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun authenticationNodeLogDAO(): AuthenticationNodeLogDAO

    companion object {
        fun getAppDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "authenticationLogs").allowMainThreadQueries().build()
        }
    }
}
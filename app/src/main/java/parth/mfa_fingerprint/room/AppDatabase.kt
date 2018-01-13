package parth.mfa_fingerprint.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 * Created by Parth Chandratreya on 13/01/2018.
 */

@Database(entities = arrayOf(AuthenticationNodeLogTable::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun authenticationNodeLogDAO(): AuthenticationNodeLogDAO
}
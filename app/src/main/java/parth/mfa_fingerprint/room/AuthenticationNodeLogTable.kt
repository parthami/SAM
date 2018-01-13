package parth.mfa_fingerprint.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.time.LocalDateTime

/**
 * Created by Parth Chandratreya on 13/01/2018.
 */
    @Entity
    data class AuthenticationNodeLogTable(@ColumnInfo(name = "label") var label: String = "",
                                           @ColumnInfo(name = "dateTime") var dateTime: LocalDateTime = LocalDateTime.now(),
                                           @ColumnInfo(name = "result") var result: Boolean = false) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}


package parth.mfa_fingerprint.room

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE

/**
 * Created by Parth Chandratreya on 13/01/2018.
 */
@Dao
interface AuthenticationNodeLogDAO {

    @Query("select * from AuthenticationNodeLog")
    fun getAllTasks(): List<AuthenticationNodeLog>

//    @Query("select * from AuthenticationNodeLog where id = :p0")
//    fun findTaskById(id: Long): AuthenticationNodeLog

    @Insert(onConflict = REPLACE)
    fun insertTask(task: AuthenticationNodeLog)

    @Update(onConflict = REPLACE)
    fun updateTask(task: AuthenticationNodeLog)

    @Delete
    fun deleteTask(task: AuthenticationNodeLog)
}
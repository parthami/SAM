package parth.mfa_fingerprint.room

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE

/**
 * Created by Parth Chandratreya on 13/01/2018.
 */
@Dao
interface AuthenticationNodeLogDAO {

    @Query("select * from AuthenticationNodeLogTable")
    fun getAllTasks(): List<AuthenticationNodeLogTable>

    @Query("select * from task where id = :p0")
    fun findTaskById(id: Long): AuthenticationNodeLogTable

    @Insert(onConflict = REPLACE)
    fun insertTask(task: AuthenticationNodeLogTable)

    @Update(onConflict = REPLACE)
    fun updateTask(task: AuthenticationNodeLogTable)

    @Delete
    fun deleteTask(task: AuthenticationNodeLogTable)
}
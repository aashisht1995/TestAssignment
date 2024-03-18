package sg.whyq.testassignment.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import sg.whyq.testassignment.ui.models.UserData

@Dao
interface UserDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userData: UserData): Long

    @Update
    fun update(userData: UserData)

    @Query("select * from user_data_table order by id asc")
    fun getAllUserEntries(): List<UserData>

    @Query("UPDATE user_data_table SET deleted=1 WHERE id = :id")
    fun deleteEntryByID(id: Int)

    @Query("DELETE FROM user_data_table")
    fun deleteAllEntries()

    @Query("select * from user_data_table WHERE id = :id")
    fun getUserById(id: Int): List<UserData>


    @Query("SELECT EXISTS(SELECT * FROM user_data_table WHERE id = :id)")
    fun isUserExist(id : Int) : Boolean
}
package sg.whyq.testassignment.database

import android.app.Application
import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import sg.whyq.testassignment.ui.models.UserData

class UserDataRepository(application: Application, context: Context) {

    private var userDataDao: UserDataDao
    private val database = AppDatabase.getInstance(application)

    init {
        userDataDao = database.userDataDao()
    }

    suspend fun insert(userData: UserData): Long {
        return try {
            withContext(Dispatchers.IO) {
                userDataDao.insert(userData)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    fun update(userData: UserData) {
        userDataDao.update(userData)
    }

    fun getAllUserEntries(): List<UserData> {
        return userDataDao.getAllUserEntries()
    }

    fun deleteEntryByID(entryId: Int) {
        return userDataDao.deleteEntryByID(entryId)
    }

    fun deleteAllEntries() {
        return userDataDao.deleteAllEntries()
    }

    fun getUserById(id:Int): List<UserData> {
        return userDataDao.getUserById(id)
    }

    fun isUserExist(id:Int): Boolean {
        return userDataDao.isUserExist(id)
    }


}
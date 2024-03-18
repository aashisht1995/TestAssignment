package sg.whyq.testassignment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import sg.whyq.testassignment.ui.models.UserData

@Database(
    entities = [UserData::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDataDao(): UserDataDao

    companion object {

        private var instance: AppDatabase? = null
        private var context: Context? = null

        @Synchronized
        fun getInstance(ctx: Context): AppDatabase {

            context = ctx

            if (instance == null) {
                try {
                    instance = Room.databaseBuilder(ctx.applicationContext, AppDatabase::class.java, "test_assignment_by_ashish_database")
                        // .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .allowMainThreadQueries()
                        .build()

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            return instance!!
        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                populateDatabase(instance!!)
            }
        }

        private fun populateDatabase(db: AppDatabase) {
            if (context != null) {

            }
        }
    }
}
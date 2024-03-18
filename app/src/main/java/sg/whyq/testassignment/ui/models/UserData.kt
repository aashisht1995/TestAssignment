package sg.whyq.testassignment.ui.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import java.io.Serializable

@Entity(tableName = "user_data_table")
class UserData : Serializable {

    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
    var email: String = ""
    var first_name: String = ""
    var last_name: String = ""
    var avatar: String = ""
    var deleted: Int = 0

    fun clone(): UserData {
        val json = Gson().toJson(this, UserData::class.java)
        return Gson().fromJson(json, UserData::class.java)
    }
}
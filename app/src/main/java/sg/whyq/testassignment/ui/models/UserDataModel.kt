package sg.whyq.testassignment.ui.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

data class UserDataModel(
    val page: Int = 0,
    val per_page: Int = 0,
    val total: Int = 0,
    val total_pages: Int = 0,
    val message: String = "",
    val data: ArrayList<UserData>
) : Serializable



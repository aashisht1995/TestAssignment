package sg.whyq.testassignment.network_service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import sg.whyq.testassignment.ui.models.UserDataModel

interface ApiMethodInterface {

    @GET("users?")
    fun getUsersList(@Query("page") pageNumber: String): Call<UserDataModel>
}
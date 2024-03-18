package sg.whyq.testassignment.network_service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val baseUrl = "https://reqres.in/api/"

    val api: ApiMethodInterface by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiMethodInterface::class.java)
    }
}
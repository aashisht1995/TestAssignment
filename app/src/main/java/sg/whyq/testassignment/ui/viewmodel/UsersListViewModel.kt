package sg.whyq.testassignment.ui.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sg.whyq.testassignment.MyApplication
import sg.whyq.testassignment.R
import sg.whyq.testassignment.database.AppDatabase
import sg.whyq.testassignment.database.UserDataRepository
import sg.whyq.testassignment.network_service.RetrofitInstance
import sg.whyq.testassignment.ui.models.UserData
import sg.whyq.testassignment.ui.models.UserDataModel
import sg.whyq.testassignment.utills.CommonFunctions
import kotlin.coroutines.CoroutineContext

class UsersListViewModel : ViewModel() {

    private var TAG = this.javaClass.name

    val showErrorLiveData = MutableLiveData<String>()

    val showFirstProgress = MutableLiveData<Boolean>()
    val showLoadMoreProgress = MutableLiveData<Boolean>()
    val loadMoreVariable = MutableLiveData<Boolean>()

    private val userDataArrayList = ArrayList<UserData>()
    private var userDataLiveData = MutableLiveData<ArrayList<UserData>>()

    fun getPopularMovies(pageNumber: Int, mActivity: Activity) {

        if (pageNumber == 1) {
            showFirstProgress.postValue(true)
        }

        RetrofitInstance.api.getUsersList(pageNumber.toString()).enqueue(object : Callback<UserDataModel> {
            override fun onResponse(call: Call<UserDataModel>, response: Response<UserDataModel>) {

                Log.e(TAG, "onResponse :: ${response.body().toString()}")

                if (response.body() != null) {

                    val userDataModel = response.body()!!
                    val pageNumber = userDataModel.page
                    val total = userDataModel.total
                    val dataList = userDataModel.data
                    val message = userDataModel.message

                    if (pageNumber == 1) {
                        userDataArrayList.clear()
                        clearAllDatabase()
                    }

                    userDataArrayList.addAll(dataList)

                    if (userDataArrayList.size > 0) {
                        setUsersLiveData(userDataArrayList)
                    } else {
                        showErrorLiveData.postValue(message)
                    }

                    if (pageNumber > 1 && dataList.isNullOrEmpty()) {
                        CommonFunctions.showSnackBar(mActivity, mActivity.getString(R.string.no_more_data_found))
                    }

                    if (pageNumber > 4) {
                        loadMoreVariable.postValue(false)
                    } else
                        loadMoreVariable.postValue(true)
//                        loadMoreVariable.postValue(userDataArrayList.size < total)

                }

                if (pageNumber != 1) {
                    showLoadMoreProgress.postValue(false)
                } else
                    showFirstProgress.postValue(false)
            }

            override fun onFailure(call: Call<UserDataModel>, t: Throwable) {
                val message = t.message.toString()
                Log.e(TAG, message)
                showErrorLiveData.postValue(message)
            }
        })
    }

    private fun setUsersLiveData(usersDataList: ArrayList<UserData>) {
        userDataLiveData.postValue(usersDataList)
    }

    fun getUserDataLiveData(): LiveData<ArrayList<UserData>> {
        return userDataLiveData
    }


    fun getErrorLiveData(): LiveData<String?> {
        return showErrorLiveData
    }

    fun getLoadMoreProgress(): LiveData<Boolean> {
        return showLoadMoreProgress
    }

    fun getFirstProgress(): LiveData<Boolean> {
        return showFirstProgress
    }

    fun getLoadMoreVariable(): LiveData<Boolean> {
        return loadMoreVariable
    }

    fun clearAllDatabase(){
        val myApplication = MyApplication().getInstance()!!
        val database = AppDatabase.getInstance(myApplication)
        database.clearAllTables()
    }


}
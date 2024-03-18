package sg.whyq.testassignment

import android.app.Application
import android.content.Context

class MyApplication : Application() {

    private var mInstance: MyApplication? = null
    private var context: Context? = null

    override fun onCreate() {
        super.onCreate()

        context = this
        mInstance = this
    }

    companion object {


    }

    @Synchronized
    fun getInstance(): MyApplication? {
        return mInstance
    }

    init {
        mInstance = this
        context = this
    }

}
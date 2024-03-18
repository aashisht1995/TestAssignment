package sg.whyq.testassignment.utills

import android.content.Context
import android.net.ConnectivityManager

object ConnectionDetector {
    fun isConnectingToInternet(_context: Context?): Boolean {
        val connectivityManager = _context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}
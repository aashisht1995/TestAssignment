package sg.whyq.testassignment.utills

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.content.ContextCompat.startActivity
import com.google.android.material.snackbar.Snackbar
import sg.whyq.testassignment.R


object CommonFunctions {

    fun showSnackBar(mActivity: Activity?, message: String?) {
        val snackBar = Snackbar.make(
            mActivity!!.findViewById(android.R.id.content),
            message!!,
            Snackbar.LENGTH_LONG
        )
        snackBar.setAction(R.string.txt_dismiss) {
            snackBar.dismiss()
        }

        snackBar.show()
    }

    fun fromHtml(source: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(source, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(source)
        }
    }

    fun sendEmailIntent(mContext: Context, emailAddress:String){
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/html")
        intent.putExtra(Intent.EXTRA_EMAIL, emailAddress)
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
        intent.putExtra(Intent.EXTRA_TEXT, mContext.getString(R.string.send_via_testassignment_app))
        mContext.startActivity(Intent.createChooser(intent, mContext.getString(R.string.send_email_via)))
    }
}
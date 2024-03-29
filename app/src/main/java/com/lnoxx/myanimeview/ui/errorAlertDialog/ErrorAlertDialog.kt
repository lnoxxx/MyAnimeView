package com.lnoxx.myanimeview.ui.errorAlertDialog

import android.content.Context
import android.view.LayoutInflater
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lnoxx.myanimeview.R

class ErrorAlertDialog(private val context: Context, message: String?, negativeButtonText: String) {
    private val view = LayoutInflater.from(context).inflate(R.layout.alert_dialoge_error_layout, null)
    private val alertDialog = MaterialAlertDialogBuilder(context)
        .setTitle(context.getString(R.string.errorTitle))
        .setView(view)
        .setMessage(getRecommendation(message?: ""))
        .setPositiveButton(negativeButtonText) { dialog, _ -> dialog.dismiss()}
        .create()
    fun showDialog(){
        alertDialog.show()
    }
    private fun getRecommendation(error: String): String{
        return when(error){
            "Unable to resolve host \"api.jikan.moe\": No address associated with hostname"
            -> context.getString(R.string.errorInternet)
            else -> context.getString(R.string.errorUnknown)
        }
    }
}
package com.example.mynotesapp.Utils

import android.app.Activity
import android.util.Log
import android.widget.Toast

fun Activity.printLog(str:String){
    Log.e(this.toString(),str)
}

fun Activity.showToast(msg : String ){
    Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
}
package com.ecwid.warehouse.utils

import android.content.Context

fun toast(context: Context?, text: String?) {
    if (context != null && text != null) {
        val toast = android.widget.Toast.makeText(context, text, android.widget.Toast.LENGTH_SHORT)
        toast.show()
    }
}


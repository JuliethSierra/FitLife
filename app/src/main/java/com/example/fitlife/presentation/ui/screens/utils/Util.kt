package com.example.fitlife.presentation.ui.screens.utils

import android.content.Context
import android.widget.Toast

class Util {
    companion object {
        fun showAShortMessage(stringId: String, context: Context) {
            Toast.makeText(context, stringId, Toast.LENGTH_SHORT).show()
        }

        fun showALongMessage(stringId: String, context: Context) {
            Toast.makeText(context, stringId, Toast.LENGTH_LONG).show()
        }

        fun isAValidEmail(email: String): Boolean {
            val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$")
            return emailRegex.matches(email)
        }
    }
}
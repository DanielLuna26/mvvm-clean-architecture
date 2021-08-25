package com.moon.mvvm_clean.utils

import java.util.regex.Pattern

fun isEmailValid(email: String?) : Boolean {
    if (email.isNullOrEmpty()) {
        return false
    }
    val pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
    return pattern.matcher(email).matches()
}

fun isValidFieldRequired(field: String?) : Boolean {
    return !field.isNullOrEmpty()
}
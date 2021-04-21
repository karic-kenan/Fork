/*
 * Created by Karic Kenan on 20.4.2021
 * Copyright (c) 2021 . All rights reserved.
 */

package io.aethibo.fork.ui.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

fun formatLargeNumber(number: Int) = when {
    number > 100000 -> "${number.toString().substring(0, 3)}k"
    number > 10000 -> "${number.toString().substring(0, 2)}k"
    number > 1000 -> "${number.toString().substring(0, 1)}k"
    else -> number.toString()
}

fun Fragment.hideKeyboard() {
    requireView().let { requireActivity().hideKeyboard(it) }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}
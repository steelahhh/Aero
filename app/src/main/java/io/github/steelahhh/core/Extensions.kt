package io.github.steelahhh.core

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.net.toUri

fun View.showSoftKeyboard() {
    if (requestFocus()) {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun View.hideSoftKeyboard() {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}

val String.lastSegmentOrZero get() = toUri().lastPathSegment?.toIntOrNull() ?: 0

package io.aethibo.fork.ui.auth.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun openNewTabWindow(context: Context, url: String) {
    val uri = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, uri)
    context.startActivity(intent)
}
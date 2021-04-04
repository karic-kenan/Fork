package io.aethibo.data.utils

import java.util.*

fun String.tokenize(tokenType: String) = "${tokenType.capitalize(Locale.ROOT)} $this"
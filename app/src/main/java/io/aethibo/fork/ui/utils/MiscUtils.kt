/*
 * Created by Karic Kenan on 20.4.2021
 * Copyright (c) 2021 . All rights reserved.
 */

package io.aethibo.fork.ui.utils

fun formatLargeNumber(number: Int) = when {
    number > 100000 -> "${number.toString().substring(0, 3)}k"
    number > 10000 -> "${number.toString().substring(0, 2)}k"
    number > 1000 -> "${number.toString().substring(0, 1)}k"
    else -> number.toString()
}
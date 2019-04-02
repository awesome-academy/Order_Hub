package com.trunghoang.orderhub.utils

import java.text.NumberFormat
import java.util.*

class FormatUtils {
    companion object {
        fun formatCurrency(number: Long): String =
            NumberFormat.getNumberInstance(Locale.getDefault()).format(number)
    }
}

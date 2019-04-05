package com.trunghoang.orderhub.utils

import java.text.Normalizer
import java.text.NumberFormat
import java.util.*
import java.util.regex.Pattern

class FormatUtils {
    companion object {
        const val REGEX_REMOVE_ACCENT = "\\p{InCombiningDiacriticalMarks}+"
        const val REGEX_REMOVE_SPACES = "\\s"
        fun formatCurrency(number: Long): String =
            NumberFormat.getNumberInstance(Locale.getDefault()).format(number)
    }
}

fun String.standardize(): String {
    val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
    val pattern = Pattern.compile(FormatUtils.REGEX_REMOVE_ACCENT)
    return pattern.matcher(temp)
        .replaceAll("")
        .toLowerCase().replace(FormatUtils.REGEX_REMOVE_SPACES.toRegex(), "")
}

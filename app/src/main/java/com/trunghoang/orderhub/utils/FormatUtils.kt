package com.trunghoang.orderhub.utils

import java.text.Normalizer
import java.text.NumberFormat
import java.util.*
import java.util.regex.Pattern

object FormatUtils {
    const val REGEX_REMOVE_ACCENT = "\\p{InCombiningDiacriticalMarks}+"
    const val REGEX_REMOVE_SPACES = "\\s"
    @JvmStatic
    fun longToString(number: Long?): String =
        number?.let {
            NumberFormat.getNumberInstance(Locale.getDefault()).format(it)
        } ?: ""

    @JvmStatic
    fun stringToLong(s: String?) =
        if (s.isNullOrBlank()) null else
            NumberFormat.getNumberInstance(Locale.getDefault()).parse(s)
}

fun String.standardize(): String {
    val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
    val pattern = Pattern.compile(FormatUtils.REGEX_REMOVE_ACCENT)
    return pattern.matcher(temp)
        .replaceAll("")
        .toLowerCase().replace(FormatUtils.REGEX_REMOVE_SPACES.toRegex(), "")
}

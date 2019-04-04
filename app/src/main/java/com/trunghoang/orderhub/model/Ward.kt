package com.trunghoang.orderhub.model

import com.google.gson.annotations.SerializedName

class Ward(
    @SerializedName(WARD_CODE)
    val code: String,
    @SerializedName(WARD_NAME)
    val name: String
) {
    companion object {
        const val WARD_CODE = "WardCode"
        const val WARD_NAME = "WardName"
    }
}

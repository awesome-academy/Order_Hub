package com.trunghoang.orderhub.model

import com.google.gson.annotations.SerializedName

data class District(
    @SerializedName(value = DISTRICT_ID)
    val id: Int,
    @SerializedName(value = DISTRICT_NAME)
    val name: String,
    @SerializedName(value = PROVINCE_NAME)
    val provinceName: String,
    @SerializedName(value = SUPPORT_TYPE)
    val supportType: Int
) {
    companion object {
        const val DISTRICT_ID = "DistrictID"
        const val DISTRICT_NAME = "DistrictName"
        const val PROVINCE_NAME = "ProvinceName"
        const val SUPPORT_TYPE = "SupportType"
    }
    val fullName
        get() = "$provinceName - $name"
}

package com.trunghoang.orderhub.model

import com.google.gson.annotations.SerializedName

data class District(
    @SerializedName(DISTRICT_ID)
    val id: Int? = null,
    @SerializedName(DISTRICT_NAME)
    val name: String? = null,
    @SerializedName(PROVINCE_NAME)
    val provinceName: String? = null,
    @SerializedName(SUPPORT_TYPE)
    val supportType: Int? = null
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

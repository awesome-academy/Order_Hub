package com.trunghoang.orderhub.model

data class District(
    val DistrictID: Int,
    val DistrictName: String,
    val ProvinceName: String,
    val SupportType: Int
) {
    val fullName
        get() = "$ProvinceName - $DistrictName"
}

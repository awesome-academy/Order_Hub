package com.trunghoang.orderhub.model

import com.google.gson.annotations.SerializedName

class GHNApiRequest {
    companion object {
        const val DEFAULT_FROM_DISTRICT_ID = 1485
        const val DEFAULT_SERVICE_ID = 53322
        const val DEFAULT_WEIGHT = 500
        const val DISTRICT_ID = "DistrictID"
        const val FROM_DISTRICT_ID = "FromDistrictID"
        const val TO_DISTRICT_ID = "ToDistrictID"
        const val SERVICE_ID = "ServiceID"
        const val WEIGHT = "Weight"
        const val LENGTH = "Length"
        const val WIDTH = "Width"
        const val HEIGHT = "Height"
        const val ORDER_COSTS = "OrderCosts"
        const val COUPON_CODE = "CouponCode"
        const val INSURANCE_FEE = "InsuranceFee"
    }

    class Districts(
        val token: String
    )

    class Wards(
        val token: String,
        @SerializedName(DISTRICT_ID)
        val districtId: Int? = null
    )

    class Fee(
        val token: String,
        @SerializedName(FROM_DISTRICT_ID)
        val fromDistrictID: Int = DEFAULT_FROM_DISTRICT_ID,
        @SerializedName(TO_DISTRICT_ID)
        val toDistrictID: Int,
        @SerializedName(SERVICE_ID)
        val serviceID: Int = DEFAULT_SERVICE_ID,
        @SerializedName(WEIGHT)
        val weight: Int = DEFAULT_WEIGHT,
        @SerializedName(LENGTH)
        val length: Int? = null,
        @SerializedName(WIDTH)
        val width: Int? = null,
        @SerializedName(HEIGHT)
        val height: Int? = null,
        @SerializedName(ORDER_COSTS)
        val orderCosts: Any? = null,
        @SerializedName(COUPON_CODE)
        val couponCode: String? = null,
        @SerializedName(INSURANCE_FEE)
        val insuranceFee: Int? = null
    )

    class Services(
        val token: String,
        @SerializedName(FROM_DISTRICT_ID)
        val fromDistrictID: Int = DEFAULT_FROM_DISTRICT_ID,
        @SerializedName(TO_DISTRICT_ID)
        val toDistrictID: Int,
        @SerializedName(WEIGHT)
        val weight: Int? = null,
        @SerializedName(LENGTH)
        val length: Int? = null,
        @SerializedName(WIDTH)
        val width: Int? = null,
        @SerializedName(HEIGHT)
        val height: Int? = null
    )
}

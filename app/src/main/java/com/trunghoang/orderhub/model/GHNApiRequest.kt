package com.trunghoang.orderhub.model

class GHNApiRequest {
    companion object {
        const val DEFAULT_FROM_DISTRICT_ID = 1485
        const val DEFAULT_SERVICE_ID = 53322
        const val DEFAULT_WEIGHT = 500
    }

    class Districts(
        val token: String
    )

    class Wards(
        val token: String,
        val DistrictID: Int? = null
    )

    class Fee(
        val token: String,
        val FromDistrictID: Int = DEFAULT_FROM_DISTRICT_ID,
        val ToDistrictID: Int,
        val ServiceID: Int = DEFAULT_SERVICE_ID,
        val Weight: Int = DEFAULT_WEIGHT,
        val Length: Int? = null,
        val Width: Int? = null,
        val Height: Int? = null,
        val OrderCosts: Any? = null,
        val CouponCode: String? = null,
        val InsuranceFee: Int? = null
    )

    class Services(
        val token: String,
        val FromDistrictID: Int = DEFAULT_FROM_DISTRICT_ID,
        val ToDistrictID: Int,
        val Weight: Int? = null,
        val Length: Int? = null,
        val Width: Int? = null,
        val Height: Int? = null
    )
}

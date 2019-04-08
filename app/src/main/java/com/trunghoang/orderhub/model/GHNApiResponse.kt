package com.trunghoang.orderhub.model

import com.google.gson.annotations.SerializedName

class GHNApiResponse {
    companion object {
        const val WARDS = "Wards"
        const val CALCULATED_FEE = "CalculatedFee"
        const val EXPECTED_DELIVERY_TIME = "ExpectedDeliveryTime"
        const val EXTRAS = "Extras"
        const val NAME = "Name"
        const val SERVICE_FEE = "ServiceFee"
        const val SERVICE_ID = "ServiceID"
        const val MAX_VALUE = "MaxValue"
    }
    class Wards(
        @SerializedName(WARDS)
        val wards: List<Ward>
    )

    class Fee(
        @SerializedName(CALCULATED_FEE)
        val calculatedFee: Int
    )

    class Service(
        @SerializedName(EXPECTED_DELIVERY_TIME)
        val expectedDeliveryTime: String,
        @SerializedName(EXTRAS)
        val extras: List<Extra>,
        @SerializedName(NAME)
        val name: String,
        @SerializedName(SERVICE_FEE)
        val serviceFee: Int,
        @SerializedName(SERVICE_ID)
        val serviceID: Int
    ) {
        companion object {
            const val DEFAULT_SERVICE_NAME = "Chuẩn"
        }
        class Extra(
            @SerializedName(MAX_VALUE)
            val maxValue: Int,
            @SerializedName(NAME)
            val name: String,
            @SerializedName(SERVICE_FEE)
            val serviceFee: Int,
            @SerializedName(SERVICE_ID)
            val serviceID: Int
        )
    }
}

package com.trunghoang.orderhub.model

class GHNApiResponse {
    class Wards(
        val Wards: List<Ward>
    )

    class Fee(
        val CalculatedFee: Int
    )

    class Service(
        val ExpectedDeliveryTime: String,
        val Extras: List<Extra>,
        val Name: String,
        val ServiceFee: Int,
        val ServiceID: Int
    ) {
        companion object {
            const val DEFAULT_SERVICE_NAME = "Chuẩn"
        }

        class Extra(
            val MaxValue: Int,
            val Name: String,
            val ServiceFee: Int,
            val ServiceID: Int
        )
    }
}

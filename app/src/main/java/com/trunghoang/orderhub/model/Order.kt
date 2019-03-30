package com.trunghoang.orderhub.model

data class Order(
    var createdTime: Long = 0,
    var lastModified: Long = 0,
    var name: String = "",
    var mobile: String = "",
    var cod: Long = 0,
    var address: String = "",
    var status: Int = 0,
    var products: List<Product> = ArrayList()
)

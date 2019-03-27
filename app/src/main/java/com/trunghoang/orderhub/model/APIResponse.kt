package com.trunghoang.orderhub.model

class APIResponse<T>(
    var status: EnumStatus,
    var data: T?,
    var error: Throwable?
) {
    companion object {
        const val NO_VALUE = "No Value"
        fun <T> loading() = APIResponse<T>(EnumStatus.LOADING, null, null)
        fun <T> success(data: T) =
            APIResponse<T>(EnumStatus.SUCCESS, data, null)

        fun <T> error(error: Throwable) =
            APIResponse<T>(EnumStatus.ERROR, null, error)
    }
}

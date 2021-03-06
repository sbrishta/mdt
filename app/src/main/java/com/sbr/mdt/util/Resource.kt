package com.sbr.mdt.util

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val errorCode: Int? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(data: T? = null,message: String) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
    class NetworkError<T>(errorMessage:Int?) : Resource<T>(null,null,errorMessage)
}

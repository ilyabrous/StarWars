package com.example.starwarsapi.domain

import java.io.IOException

open class AppException : RuntimeException()

class EmptyFieldException(
    val field: Field
) : AppException()


class StorageException: AppException()

class NetworkException : IOException() {

    override fun toString(): String {
        return "NetworkException()"
    }
}

enum class Field {
    Email,
    Username,
    Password
}



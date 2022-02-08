package com.example.starwarsapi.domain

open class AppException : RuntimeException()

class EmptyFieldException(
    val field: Field
) : AppException()


class StorageException: AppException()

class NetworkException : AppException()

enum class Field {
    Email,
    Username,
    Password
}

package com.example.base.network


sealed class DataException(val message: String) {
    class NoNetwork(message: String) : DataException(message)
    class UnknownException(message: String) : DataException(message)
}
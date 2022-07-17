package com.lafierage.response

interface Response {
    val status: String
    val message: String

    companion object {
        const val SUCCESS = "SUCCESS"
        const val NOT_FOUND = "NOT_FOUND"
        const val FAILED = "FAILED"
        const val UNAUTHORIZED = "UNAUTHORIZED"
    }
}
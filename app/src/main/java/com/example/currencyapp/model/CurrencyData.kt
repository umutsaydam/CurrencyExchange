package com.example.currencyapp.model

data class CurrencyData(
    val success: Boolean,
    val result: Result
)

data class Result(
    val base: String,
    val lastupdate: String,
    val data: List<Currency>
)

data class Currency(
    val code: String,
    val name: String,
    val rate: String,
    val calculatedstr: String,
    val calculated: Double
)

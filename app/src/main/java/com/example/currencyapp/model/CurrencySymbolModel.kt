package com.example.currencyapp.model

data class CurrencySymbolModel(
    val success: Boolean,
    val result: List<Symbols>
)

data class Symbols(
    val code: String,
    val name: String
)

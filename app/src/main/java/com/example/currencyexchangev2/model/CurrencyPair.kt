package com.example.currencyexchangev2.model

class CurrencyPair (val sell: String, val buy: String) {
    override fun toString(): String {
        return "${sell} - ${buy}"
    }
}

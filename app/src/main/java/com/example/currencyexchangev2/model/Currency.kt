package com.example.currencyexchangev2.model

class Currency (val key: String, val value: String) {
    override fun toString(): String {
        return "${key} - ${value}"
    }
}


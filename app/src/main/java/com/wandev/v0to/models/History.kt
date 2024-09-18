package com.wandev.v0to.models

data class History(
    val id : String,
    val status : String,
    val transactions : List<Transaction>,
    val totalPrice : Int
)

data class Transaction(
    val name : String,
    val qty : Int,
    val subtotal : Int
)

package com.wandev.v0to.models

import android.graphics.Bitmap

data class Cart(
    val id : Int,
    val name : String,
    val sellerShop : String,
    val photo : Bitmap,
    var qty : Int = 0,
    val price : Int,
    var checked : Boolean = false
)

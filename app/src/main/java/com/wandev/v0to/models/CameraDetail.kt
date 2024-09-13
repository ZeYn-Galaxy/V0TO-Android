package com.wandev.v0to.models

import android.graphics.Bitmap

data class CameraDetail(
    val id : Int,
    val name : String,
    val sellerShop : String,
    val sensor : String,
    val resolution : String,
    val autoFocusSystem : String,
    val isoRange : String,
    val shuterSpeedRange : String,
    val dimensions : String,
    val weight : Int,
    val wiFi : Boolean,
    val touchScreen : Boolean,
    val flash : Boolean,
    val bluetooth : Boolean,
    val price : Int,
    val photo : Bitmap
)

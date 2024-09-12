package com.wandev.v0to.models

import android.graphics.Bitmap

data class Camera(
    val id : String,
    val name : String,
    val resolution : String,
    val price : Int,
    val photo : Bitmap
)

package com.example.a51cd865a26fe10ab2fb316fe6528f889.util

import java.lang.Math.sqrt
import kotlin.math.pow


object Util {
    fun distanceFormula(x1:Double,x2:Double,y1:Double,y2:Double):Double{
        return sqrt((x1 - x2).pow(2) + (y1 - y2).pow(2))
    }
}
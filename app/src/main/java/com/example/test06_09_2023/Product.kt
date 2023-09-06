package com.example.test06_09_2023

import java.io.Serializable

data class Product(
    val title: String,
    val description: String,
    val price: Int,
    val brand: String,
    val thumbnail: String,
    val images: ArrayList<String>
) : Serializable

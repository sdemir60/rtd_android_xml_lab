package com.example.rtd_android_xml_lab

import com.squareup.moshi.JsonClass
import kotlinx.serialization.Serializable

@Serializable
@JsonClass(generateAdapter = true)
data class UserModel(
    val Id: Int,
    val Name: String,
)
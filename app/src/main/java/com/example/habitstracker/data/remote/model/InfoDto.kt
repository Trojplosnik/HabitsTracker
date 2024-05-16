package com.example.habitstracker.data.remote.model

import com.google.gson.annotations.SerializedName

class InfoDto(
    @SerializedName("code") var code: Int? = null,
    @SerializedName("message") var message: String? = null
)
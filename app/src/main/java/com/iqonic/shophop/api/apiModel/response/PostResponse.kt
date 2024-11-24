package com.iqonic.shophop.api.apiModel.response

import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("message")
    val message: String
)

package com.iqonic.phonphon_store.api.apiModel.response

import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("message")
    val message: String
)

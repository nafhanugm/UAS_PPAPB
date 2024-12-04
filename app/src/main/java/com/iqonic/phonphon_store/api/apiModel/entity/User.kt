package com.iqonic.phonphon_store.api.apiModel.entity

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("_id")
    val id: String?,

    @SerializedName("password")
    val password: String?,

    @SerializedName("first_name")
    val firstName: String?,

    @SerializedName("last_name")
    val lastName: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("profile_image")
    val profileImage: String?,

    @SerializedName("role")
    val role: UserRole? = UserRole.USER
)

enum class UserRole(val value: String) {
    ADMIN("ADMIN"),
    USER("USER")
}
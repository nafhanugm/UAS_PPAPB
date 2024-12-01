package com.iqonic.shophop.api.repository

import android.util.Log
import com.iqonic.shophop.api.ApiClient
import com.iqonic.shophop.api.apiModel.entity.UserModel
import com.iqonic.shophop.api.apiModel.response.PostResponse
import com.iqonic.shophop.models.RequestModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserRepository {
    companion object{
        fun getUsers() {
             ApiClient.getInstance().getUsers().enqueue(object : Callback<List<UserModel>> {
                 override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                     Log.e("Error", t.message)
                 }

                 override fun onResponse(call: Call<List<UserModel>>, response: Response<List<UserModel>>) {
                     Log.e("Response", response.body().toString())
                 }
             })
        }

        suspend fun getUserByEmail(email: String): Result<UserModel?> = suspendCoroutine { continuation ->
            ApiClient.getInstance().getUsers().enqueue(object : Callback<List<UserModel>> {
                override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                    continuation.resume(Result.failure(t))
                }

                override fun onResponse(call: Call<List<UserModel>>, response: Response<List<UserModel>>) {
                    try {
                        val user = response.body()?.find { it.email == email }
                        continuation.resume(Result.success(user))
                    } catch (e: Exception) {
                        continuation.resume(Result.failure(e))
                    }
                }
            })
        }

        suspend fun createUser(user: RequestModel): Result<Boolean> = suspendCoroutine { continuation ->
            val userModel = UserModel(
                id = null,
                password = user.password,
                firstName = user.firstName,
                lastName = user.lastName,
                email = user.userEmail,
                profileImage = null
            )

            ApiClient.getInstance().createUser(userModel).enqueue(object : Callback<PostResponse> {
                override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                    continuation.resume(Result.failure(t))
                }

                override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                    if (response.isSuccessful) {
                        continuation.resume(Result.success(true))
                    } else {
                        continuation.resume(Result.failure(Exception("Failed to create user")))
                    }
                }
            })
        }

        suspend fun updateUser(user: RequestModel): Result<Boolean> = suspendCoroutine { continuation ->
            val newUser = UserModel(
                id = null,
                password = user.password,
                firstName = user.firstName,
                lastName = user.lastName,
                email = user.userEmail,
                profileImage = null
            )
            val email = newUser.email
            ApiClient.getInstance().getUsers().enqueue(object : Callback<List<UserModel>> {
                override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                    continuation.resume(Result.failure(t))
                }

                override fun onResponse(call: Call<List<UserModel>>, response: Response<List<UserModel>>) {
                    try {
                        val userModel = response.body()?.find { it.email == email }
                        if (userModel != null) {
                            Log.d("UserModel", userModel.toString())
                            ApiClient.getInstance().updateUser(userModel.id!!, newUser).enqueue(object : Callback<PostResponse> {
                                override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                                    continuation.resume(Result.failure(t))
                                }

                                override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                                    if (response.isSuccessful) {
                                        continuation.resume(Result.success(true))
                                    } else {
                                        Log.e("ErrorResponse", response.message())
                                        continuation.resume(Result.failure(Exception("Failed to update user")))
                                    }
                                }
                            })
                        } else {
                            continuation.resume(Result.failure(Exception("User not found")))
                        }
                    } catch (e: Exception) {
                        continuation.resume(Result.failure(e))
                    }
                }
            })
        }

        suspend fun signIn(email: String, password: String): Result<UserModel?> = suspendCoroutine { continuation ->
            ApiClient.getInstance().getUsers().enqueue(object : Callback<List<UserModel>> {
                override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                    continuation.resume(Result.failure(t))
                }

                override fun onResponse(call: Call<List<UserModel>>, response: Response<List<UserModel>>) {
                    try {
                        val user = response.body()?.find {
                            it.email == email && it.password == password
                        }
                        continuation.resume(Result.success(user))
                    } catch (e: Exception) {
                        continuation.resume(Result.failure(e))
                    }
                }
            })
        }

        suspend fun deleteUser(email: String): Result<Boolean> = suspendCoroutine { continuation ->
            ApiClient.getInstance().getUsers().enqueue(object : Callback<List<UserModel>> {
                override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                    continuation.resume(Result.failure(t))
                }

                override fun onResponse(call: Call<List<UserModel>>, response: Response<List<UserModel>>) {
                    try {
                        val user = response.body()?.find { it.email == email }
                        if (user != null) {
                            ApiClient.getInstance().deleteUser(user.id!!).enqueue(object : Callback<PostResponse> {
                                override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                                    continuation.resume(Result.failure(t))
                                }

                                override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                                    if (response.isSuccessful) {
                                        continuation.resume(Result.success(true))
                                    } else {
                                        continuation.resume(Result.failure(Exception("Failed to delete user")))
                                    }
                                }
                            })
                        } else {
                            continuation.resume(Result.failure(Exception("User not found")))
                        }
                    } catch (e: Exception) {
                        continuation.resume(Result.failure(e))
                    }
                }
            })
        }
    }


}
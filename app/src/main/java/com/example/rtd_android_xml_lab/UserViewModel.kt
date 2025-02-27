package com.example.rtd_android_xml_lab

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random
import kotlinx.serialization.json.Json

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    fun getUserName(): String {

        val  randomNumber = Random.nextInt(1, 4)

        var userName: String = "Empty";
        val user = userRepository.getUser();

        try {

            when (randomNumber) {

                1 -> {
                    val gson = Gson()
                    val userModel = gson.fromJson(user, UserModel::class.java)
                    userName = "${userModel.Name} With Gson";
                }

                2 -> {
                    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                    val adapter = moshi.adapter(UserModel::class.java).lenient()
                    val userModel = adapter.fromJson(user)
                    userName = "${userModel?.Name} With Moshi";
                }

                3 -> {
                    val userModel = Json.decodeFromString<UserModel>(user)
                    userName = "${userModel.Name} With Kotlinx.Serialization";
                }

            }

        } catch (e: Exception) {

            Log.e("UserViewModel", "Error parsing JSON: ${e.message}")
            userName = "Error parsing JSON"

        }

        return userName
    }
}
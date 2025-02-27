package com.example.rtd_android_xml_lab

import kotlin.random.Random

class UserRepository {

    private val gson = com.google.gson.Gson()

    private val OttomanSultans = listOf(
        UserModel(1, "Osman Gazi"),
        UserModel(2, "Orhan Gazi"),
        UserModel(3, "I. Murad"),
        UserModel(4, "I. Bayezid (Yıldırım)"),
        UserModel(5, "I. Mehmed (Çelebi)"),
        UserModel(6, "II. Murad"),
        UserModel(7, "II. Mehmed (Fatih)"),
        UserModel(8, "II. Bayezid"),
        UserModel(9, "I. Selim (Yavuz)"),
        UserModel(10, "I. Süleyman (Kanuni)"),
        UserModel(11, "II. Selim"),
        UserModel(12, "III. Murad"),
        UserModel(13, "III. Mehmed"),
        UserModel(14, "I. Ahmed"),
        UserModel(15, "I. Mustafa"),
        UserModel(16, "II. Osman (Genç)"),
        UserModel(17, "IV. Murad"),
        UserModel(18, "İbrahim"),
        UserModel(19, "IV. Mehmed"),
        UserModel(20, "II. Süleyman"),
        UserModel(21, "II. Ahmed"),
        UserModel(22, "II. Mustafa"),
        UserModel(23, "III. Ahmed"),
        UserModel(24, "I. Mahmud"),
        UserModel(25, "III. Osman"),
        UserModel(26, "III. Mustafa"),
        UserModel(27, "I. Abdülhamid"),
        UserModel(28, "III. Selim"),
        UserModel(29, "IV. Mustafa"),
        UserModel(30, "II. Mahmud"),
        UserModel(31, "I. Abdülmecid"),
        UserModel(32, "Abdülaziz"),
        UserModel(33, "V. Murad"),
        UserModel(34, "II. Abdülhamid"),
        UserModel(35, "V. Mehmed (Reşad)"),
        UserModel(36, "VI. Mehmed (Vahideddin)")
    )

    fun getUser(): String {

        val randomIndex = Random.nextInt(OttomanSultans.size)
        val OttomanSultan = OttomanSultans[randomIndex]

        return gson.toJson(OttomanSultan)
    }
}
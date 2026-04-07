package com.joaomarcos.lifemanager.repository

import com.google.firebase.auth.FirebaseAuth

class AuthRepository {
    private lateinit var auth: FirebaseAuth

    fun getCurrentUser(): Boolean {
        return auth.currentUser == null
    }
}
package com.joaomarcos.lifemanager.utils.validation

object Validator {
    fun validateEmail(email: String): Boolean {
        return (email.length >= 5 && email.contains("@"))
    }

    fun validatePassword(password: String): Boolean {
        return password.length >= 6
    }
}
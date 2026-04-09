package com.joaomarcos.lifemanager.utils.validation

object Validator {

    fun validateName(name: String?): Boolean {
        return (name?.length ?: 0) >= 3
    }

    fun validateUsername(username: String?): Boolean {
        return (username?.length ?: 0) >= 3
    }

    fun validateEmail(email: String?): Boolean {
        return ((email?.length ?: 0) >= 5 && (email ?: "-").contains("@"))
    }

    fun validatePassword(password: String?): Boolean {
        return (password?.length ?: 0) >= 6
    }

    fun validatePasswordConfirm(password: String?, passwordConfirm: String?): Boolean {
        return password == passwordConfirm
    }
}
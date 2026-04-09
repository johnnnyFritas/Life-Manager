package com.joaomarcos.lifemanager.model

data class Users (
     val uid: String,
     val name: String,
     val username: String,
     val email: String,
     val tasks: Map<String, Any>
)
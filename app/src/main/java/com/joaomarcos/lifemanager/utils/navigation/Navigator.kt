package com.joaomarcos.lifemanager.utils.navigation

import androidx.fragment.app.Fragment
import com.joaomarcos.lifemanager.R
import com.joaomarcos.lifemanager.ui.login.LoginFragment

object Navigator {
    fun login(fragment: Fragment) {
        fragment.requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.fragment_container, LoginFragment(), null)
            .addToBackStack(null)
            .commit()
    }
}
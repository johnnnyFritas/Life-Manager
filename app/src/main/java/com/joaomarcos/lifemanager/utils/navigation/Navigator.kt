package com.joaomarcos.lifemanager.utils.navigation

import androidx.fragment.app.Fragment
import com.joaomarcos.lifemanager.R
import com.joaomarcos.lifemanager.ui.home.HomeFragment
import com.joaomarcos.lifemanager.ui.login.LoginFragment
import com.joaomarcos.lifemanager.ui.login.RegistryFragment

object Navigator {
    fun navigateToLogin(fragment: Fragment) {
        fragment.requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.fragment_container, LoginFragment(), null)
            .addToBackStack(null)
            .commit()
    }

    fun navigateToHome(fragment: Fragment) {
        fragment.requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.fragment_container, HomeFragment(), null)
            .addToBackStack(null)
            .commit()
    }

    fun naivateToRegistry(fragment: Fragment) {
        fragment.requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.fragment_container, RegistryFragment(), null)
            .addToBackStack(null)
            .commit()
    }
}
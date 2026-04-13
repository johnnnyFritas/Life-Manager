package com.joaomarcos.lifemanager.utils.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.joaomarcos.lifemanager.R

object Navigator {
    fun navigateWithoutStackBack(currentFragmentNavigator: Fragment, nextFragmentNavigator: Fragment, args: Bundle?) {
        nextFragmentNavigator.arguments = args
        currentFragmentNavigator.requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.fragment_container, nextFragmentNavigator, null)
            .commit()
    }

    fun navigateWithStackBack(currentFragmentNavigator: Fragment, nextFragmentNavigator: Fragment, args: Bundle?) {
        nextFragmentNavigator.arguments = args
        currentFragmentNavigator.requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.fragment_container, nextFragmentNavigator, null)
            .addToBackStack(null)
            .commit()
    }
}
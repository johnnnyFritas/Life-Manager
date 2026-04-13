package com.joaomarcos.lifemanager.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.joaomarcos.lifemanager.databinding.FragmentHomeBinding
import com.joaomarcos.lifemanager.repository.AuthRepository
import com.joaomarcos.lifemanager.ui.login.LoginFragment
import com.joaomarcos.lifemanager.utils.navigation.Navigator

class HomeFragment : Fragment() {

    //declare auth object
    private val auth: AuthRepository = AuthRepository()

    //initialize binding to get views and widgets
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //fragment overrides

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //get current user, even if it's not logged yet (null)
        val user = auth.getCurrentUser()
        if(user == null) {
            //use Navigator (object class) to navigate to other fragment
            Navigator.navigateWithoutStackBack(this@HomeFragment, LoginFragment(), null)
            return
        }

        //call function to declare listeners
        declareListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //other functions

    //declare listeners
    private fun declareListeners() {

    }
}
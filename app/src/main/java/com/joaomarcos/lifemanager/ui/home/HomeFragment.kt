package com.joaomarcos.lifemanager.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.joaomarcos.lifemanager.databinding.FragmentHomeBinding
import com.joaomarcos.lifemanager.repository.AuthRepository
import com.joaomarcos.lifemanager.utils.navigation.Navigator

class HomeFragment : Fragment() {

    private val auth = AuthRepository()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = auth.getCurrentUser()
        if(user == null)
            Navigator.navigateToLogin(this)

        declairListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun declairListeners() {
        binding.imgMenu.setOnClickListener {
            auth.logout()
            Navigator.navigateToLogin(this)
        }
    }
}
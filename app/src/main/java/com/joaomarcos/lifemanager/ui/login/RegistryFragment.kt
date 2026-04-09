package com.joaomarcos.lifemanager.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.joaomarcos.lifemanager.databinding.FragmentRegistryBinding
import com.joaomarcos.lifemanager.repository.AuthRepository
import com.joaomarcos.lifemanager.utils.validation.Validator

class RegistryFragment: Fragment() {

    //declare auth object
    private val authRepository: AuthRepository = AuthRepository()

    //initialize binding to get views and widgets
    private var _binding: FragmentRegistryBinding? = null
    private val binding get() = _binding!!

    //fragment overrides

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //call function to declare listeners
        declareListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //others functions

    //declare listeners
    private fun declareListeners() {

        //user clicks on 'Enviar'
        binding.btnRegistry.setOnClickListener {

            val name: String? = binding.inputName.text?.toString()
            //validate name
            val nameVal: Boolean = Validator.validateName(name)

            val username: String? = binding.inputUsername.text?.toString()
            //validate username
            val usernameVal: Boolean = Validator.validateUsername(username)

            val email: String? = binding.inputEmail.text?.toString()
            //validate e-mail
            val emailVal: Boolean = Validator.validateEmail(email)

            val password: String? = binding.inputPass.text?.toString()
            val passwordConfirm: String? = binding.inputPassConfirm.text?.toString()
            //validate password
            val passVal: Boolean = Validator.validatePassword(password)
            val passConfirmVal: Boolean = Validator.validatePasswordConfirm(password, passwordConfirm)

            //declare validations map to know which field is incorrect
            val validations: Map<Any, Boolean> = mapOf(
                binding.inputName to true
            )

            if (nameVal && usernameVal && emailVal && passVal && passConfirmVal) {
                //registry
            }
        }
    }
}
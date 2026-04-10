package com.joaomarcos.lifemanager.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.joaomarcos.lifemanager.databinding.FragmentRegistryBinding
import com.joaomarcos.lifemanager.model.Users
import com.joaomarcos.lifemanager.repository.AuthRepository
import com.joaomarcos.lifemanager.utils.navigation.Navigator
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
            binding.warningName.text = if (nameVal) "" else "Digite um nome válido"

            val username: String? = binding.inputUsername.text?.toString()

            //validate username
            val usernameVal: Boolean = Validator.validateUsername(username)
            binding.warningUsername.text = if (usernameVal) "" else "Digite um username válido"

            val email: String? = binding.inputEmail.text?.toString()

            //validate e-mail
            val emailVal: Boolean = Validator.validateEmail(email)
            binding.warningEmail.text = if (emailVal) "" else "Digite um e-mail válido"

            val password: String? = binding.inputPass.text?.toString()
            val passwordConfirm: String? = binding.inputPassConfirm.text?.toString()

            //validate password
            val passVal: Boolean = Validator.validatePassword(password)
            binding.warningPass.text = if (passVal) "" else "Digite uma senha válida"
            val passConfirmVal: Boolean = Validator.validatePasswordConfirm(password, passwordConfirm)
            binding.warningPass.text = if (passConfirmVal) "" else "As senhas devem ser iguais"
            binding.warningPassConfirm.text = if (passConfirmVal) "" else "As senhas devem ser iguais"

            //verify if user can register, if all validations are true, then can, if they aren't, then can't
            if (nameVal && usernameVal && emailVal && passVal && passConfirmVal) {
                //first the user needs to be authenticated, that will generate uid. declare a Task<AuthResult> variable to save the register response
                val authResultTask: Task<AuthResult> = authRepository.register(email!!, password!!)

                //verify register response
                authResultTask
                    //success
                    .addOnSuccessListener {

                        //get users uid from authentication
                        val uid: String = authResultTask.result?.user?.uid.toString()

                        //create users: Users
                        val users: Users = Users(uid, name!!, username!!, email, null)
                        //API requests to register user
                        //...

                        Navigator.navigateToHome(this)
                    }
                    //failure
                    .addOnFailureListener {

                        //show error to user
                        Toast.makeText(context, "Erro ao registrar, tente novamente mais tarde...", Toast.LENGTH_LONG).show()

                        //Log error on logcat
                        Log.e("registro", "Erro: " + authResultTask.exception.toString())
                    }
            }
        }
    }
}
package com.joaomarcos.lifemanager.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.joaomarcos.lifemanager.R
import com.joaomarcos.lifemanager.data.RetrofitClient
import com.joaomarcos.lifemanager.databinding.FragmentRegistryBinding
import com.joaomarcos.lifemanager.model.Users
import com.joaomarcos.lifemanager.repository.AuthRepository
import com.joaomarcos.lifemanager.utils.navigation.Navigator
import com.joaomarcos.lifemanager.utils.validation.Validator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response

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
        binding.btnRegistry.setOnClickListener { btnRegistryClicked() }
    }

    private fun btnRegistryClicked() {

        //get name
        val name: String? = binding.inputName.text?.toString()

        //validate name
        val nameVal: Boolean = Validator.validateName(name)
        binding.warningName.text = if (nameVal) "" else getString(R.string.invalid_name)

        //get username
        val username: String? = binding.inputUsername.text?.toString()

        //validate username
        val usernameVal: Boolean = Validator.validateUsername(username)
        binding.warningUsername.text = if (usernameVal) "" else getString(R.string.invalid_username)

        //get e-mail
        val email: String? = binding.inputEmail.text?.toString()

        //validate e-mail
        val emailVal: Boolean = Validator.validateEmail(email)
        binding.warningEmail.text = if (emailVal) "" else getString(R.string.invalid_email)

        //get password and password confirm
        val password: String? = binding.inputPass.text?.toString()
        val passwordConfirm: String? = binding.inputPassConfirm.text?.toString()

        //validate password
        val passVal: Boolean = Validator.validatePassword(password)
        val passConfirmVal: Boolean = Validator.validatePasswordConfirm(password, passwordConfirm)
        if (passVal) {
            binding.warningPass.text = if (passConfirmVal) "" else getString(R.string.invalid_password_confirm)
            binding.warningPassConfirm.text = if (passConfirmVal) "" else getString(R.string.invalid_password_confirm)
        }else {
            binding.warningPass.text = getString(R.string.invalid_password)
        }

        //verify if user can register, if all validations are true, then can, if they aren't, then can't
        if (nameVal && usernameVal && emailVal && passVal && passConfirmVal) {

            //block the button to avoid more requisitions
            binding.btnRegistry.isEnabled = false

            //show frame loading
            binding.frameLoading.visibility = View.VISIBLE
            //show loading text
            binding.txtLoading.text = getString(R.string.loading_message_sending_info)

            //first the user needs to be authenticated, that will generate uid. declare a Task<AuthResult> variable to save the register response
            val authResultTask: Task<AuthResult> = authRepository.register(email!!, password!!)

            //verify register response
            authResultTask
                //success
                .addOnSuccessListener {
                    //alert user its authenticated
                    binding.txtLoading.text = getString(R.string.loading_message_authenticated_registring)

                    //get user uid from authentication
                    val uid: String = authResultTask.result?.user?.uid.toString()

                    //create users: Users
                    val users = Users(uid, name!!, username!!, email, null)

                    lifecycleScope.launch {
                        try {
                            //insert user
                            val response = RetrofitClient.apiService.insert(users)

                            //verify response
                            if (response.isSuccessful) {
                                //alert success
                                binding.txtLoading.text = getString(R.string.loading_message_success_redirecting)
                                //delay to user read alert
                                delay(5000)

                                //success
                                Log.d("registro", "Cliente registrado: " + response.body().toString())

                                //Uses navigator to navigate to Home page
                                Navigator.navigateToHome(this@RegistryFragment)

                            } else { responseFailure(response) }
                        } catch (e: Exception) { exception(e) }

                    }

                }

                //failure
                .addOnFailureListener { authenticationFailure(authResultTask) }

        }
    }

    private fun authenticationFailure(authResultTask: Task<AuthResult>) {
        //show error to user
        Toast.makeText(requireContext(), "Erro ao registrar, tente novamente mais tarde...", Toast.LENGTH_LONG).show()

        //Log error on logcat
        Log.e("registro", "Erro: " + authResultTask.exception.toString())
    }

    private fun exception(e: Exception) {
        //show error message
        Log.e("registro", "Erro: " + e.message.toString())
    }

    private fun responseFailure(response: Response<Users>) {
        //failure
        Log.e("registro", "Erro: " + response.code())
    }
}
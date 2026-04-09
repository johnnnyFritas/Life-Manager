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
import com.joaomarcos.lifemanager.databinding.FragmentLoginBinding
import com.joaomarcos.lifemanager.repository.AuthRepository
import com.joaomarcos.lifemanager.utils.navigation.Navigator
import com.joaomarcos.lifemanager.utils.validation.Validator

class LoginFragment: Fragment() {

    private val authRepository: AuthRepository = AuthRepository()

    private var _bindind: FragmentLoginBinding? = null
    private val binding get() = _bindind!!

    //fragments overrides
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindind =  FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        declairListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        _bindind = null
    }

    //other functions
    private fun declairListeners() {
        binding.btnLogin.setOnClickListener {
            val email: String = binding.inputEmail.text.toString()
            val emailVal = Validator.validateEmail(email)
            val password: String = binding.inputPass.text.toString()
            val passVal = Validator.validatePassword(password)

            if(validateInputs(email, emailVal, password, passVal)) {
                val authResultTask: Task<AuthResult> = authRepository.login(email, password)

                authResultTask
                    .addOnSuccessListener {
                        Log.d("Usuario logado", "uid: " + authResultTask.getResult().user?.uid.toString())
                        Navigator.navigateToHome(this)
                    }
                    .addOnFailureListener {
                        Log.e("Usuario falhou ao logar", "Error: " + authResultTask.exception.toString())
                        Toast.makeText(context, "Erro ao logar, tente novamente com outros dados", Toast.LENGTH_LONG).show()
                    }
            }
        }

        binding.anchorRegister.setOnClickListener { Navigator.naivateToRegistry(this) }
    }

    fun validateInputs(email: String, emailVal: Boolean, password: String, passVal: Boolean): Boolean {

        if(email.isBlank() || !emailVal) {
            binding.warningEmail.text = "Digite um e-mail válido"
            return false
        }

        if(password.isBlank() || !passVal) {
            binding.warningPass.text = "Digite uma senha válida"
            return false
        }

        return true
    }
}
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

    //declare auth object
    private val authRepository: AuthRepository = AuthRepository()

    //initialize binding to get views and widgets
    private var _bindind: FragmentLoginBinding? = null
    private val binding get() = _bindind!!

    //fragment overrides

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindind =  FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //call function to declare listeners
        declareListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        _bindind = null
    }

    //other functions

    //declare listeners
    private fun declareListeners() {

        //user clicks on 'Entrar'
        binding.btnLogin.setOnClickListener {

            val email: String = binding.inputEmail.text.toString()
            //validate e-mail
            val emailVal = Validator.validateEmail(email)

            val password: String = binding.inputPass.text.toString()

            //verify if validation returns true of false. true means that e-mail passes the validation, false means the opposite
            if (emailVal) {

                binding.warningEmail.text = ""

                //declare a Task<AuthResult> variable to save the login response
                val authResultTask: Task<AuthResult> = authRepository.login(email, password)

                //verify login response
                authResultTask
                    //success
                    .addOnSuccessListener {

                        //set log on LOGCAT (ANDROID STUDIO)
                        Log.d("Usuario logado", "uid: " + authResultTask.result.user?.uid.toString())

                        //use Navigator (object class) to navigate to other fragment
                        Navigator.navigateToHome(this)
                    }
                    //failure
                    .addOnFailureListener {

                        //set log on LOGCAT (ANDROID STUDIO)
                        Log.e("Usuario falhou ao logar", "Error: " + authResultTask.exception.toString())

                        //Show a message to the user via TOAST
                        Toast.makeText(context, "Erro ao logar, tente novamente com outros dados", Toast.LENGTH_LONG).show()
                    }
            } else {

                binding.warningEmail.text = "Digite um e-mail válido"
            }
        }

        //user clicks on 'Não possui registro? Registre-se clicando aqui'. Then use Navigator (object class) to navigate to other fragment
        binding.anchorRegister.setOnClickListener { Navigator.naivateToRegistry(this) }
    }
}
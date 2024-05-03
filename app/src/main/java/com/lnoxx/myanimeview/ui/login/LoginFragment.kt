package com.lnoxx.myanimeview.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lnoxx.myanimeview.AnimeViewApplication
import com.lnoxx.myanimeview.MainActivity
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var application: AnimeViewApplication
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        application = requireContext().applicationContext as AnimeViewApplication
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.singInButton.setOnClickListener {
            showHide(false)
            val mail = binding.LoginEmailEditText.text.toString()
            val password = binding.LoginPasswordEditText.text.toString()
            if(!validateData(mail, password)){
                showHide(true)
                return@setOnClickListener
            }
            Firebase.auth.signInWithEmailAndPassword(mail, password).addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(context,
                        getText(R.string.singInSuccsesful),Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }else{
                    showHide(true)
                    Toast.makeText(context,
                        it.exception?.message.toString(),Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.forgotPasswordText.setOnClickListener {
            val mail = binding.LoginEmailEditText.text.toString()
            if (mail.isEmpty()){
                Toast.makeText(context,R.string.errorEmptyMail,Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Firebase.auth.sendPasswordResetEmail(mail).addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(context,
                        R.string.resetEmail,Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context,
                        it.exception?.message ?: getText(R.string.error),Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun showHide(show: Boolean){
        val status = if (show) View.VISIBLE else View.GONE
        val reverseStatus = if (!show) View.VISIBLE else View.GONE
        binding.textInputLayout.visibility = status
        binding.textInputLayout2.visibility = status
        binding.singInButton.visibility = status
        binding.SingInProgressIndicator.visibility = reverseStatus
    }

    private fun validateData(mail: String, password: String): Boolean{
        var errorHint = ""
        var hasError = false
        if (password.length < 6){
            errorHint = getString(R.string.errorPasswordShort)
            hasError = true
        }
        if(mail.isEmpty()){
            errorHint = getString(R.string.errorEmptyMail)
            hasError = true
        }
        // todo
        return if (hasError){
            Toast.makeText(context, errorHint, Toast.LENGTH_SHORT).show()
            false
        }else{
            true
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).showBottomNavBar(false)
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).showBottomNavBar(true)
    }

}
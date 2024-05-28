package com.lnoxx.myanimeview.ui.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.lnoxx.myanimeview.AnimeViewApplication
import com.lnoxx.myanimeview.MainActivity
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {
    private lateinit var application: AnimeViewApplication
    private lateinit var binding: FragmentRegistrationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater)
        application = requireContext().applicationContext as AnimeViewApplication
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.registerButton.setOnClickListener {
            showHide(false)
            val mail = binding.LoginEditText.text.toString()
            val password = binding.PasswordEditText.text.toString()
            val repeatedPassword = binding.PasswordRepeatEditText.text.toString()
            if (!validateData(mail, password, repeatedPassword)) {
                showHide(true)
                return@setOnClickListener
            }
            Firebase.auth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener {
                if (it.isSuccessful){
                    FirebaseDatabase
                        .getInstance()
                        .getReference()
                        .child("users")
                        .child(Firebase.auth.uid ?: "none")
                        .setValue(true)
                    findNavController().navigateUp()
                    Toast.makeText(context,
                        getText(R.string.registerSuccsesful),Toast.LENGTH_SHORT).show()
                }else{
                    showHide(true)
                    Toast.makeText(context,
                        it.exception?.message.toString(),Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showHide(show: Boolean){
        val status = if (show) View.VISIBLE else View.GONE
        val reverseStatus = if (!show) View.VISIBLE else View.GONE
        binding.textInputLayout.visibility = status
        binding.textInputLayout2.visibility = status
        binding.textInputLayout3.visibility = status
        binding.registerButton.visibility = status
        binding.registerProgressIndicator.visibility = reverseStatus
    }

    private fun validateData(mail: String, password: String, repeatPassword: String): Boolean{
        var errorHint = ""
        var hasError = false
        if (password != repeatPassword){
            errorHint = getString(R.string.errorPasswordNotConsists)
            hasError = true
        }
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
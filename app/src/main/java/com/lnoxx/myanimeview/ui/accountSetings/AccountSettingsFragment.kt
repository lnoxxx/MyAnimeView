package com.lnoxx.myanimeview.ui.accountSetings

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lnoxx.myanimeview.LanguageManager
import com.lnoxx.myanimeview.MainActivity
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.FragmentAccountSettingsBinding
import java.util.Locale

class AccountSettingsFragment : Fragment() {
    private lateinit var binding: FragmentAccountSettingsBinding

    companion object{
        const val PREF_NAME = "animeViewPref"
        const val KEY_THEME = "theme"
        const val THEME_LIGHT = 0
        const val THEME_DARK = 1
        const val THEME_SYSTEM_DEFAULT = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountSettingsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.AccountSetingsLayout.visibility = View.GONE
        if (Firebase.auth.currentUser != null){
            inAccountInit()
        }
        themeSettingInit()
        languageSettingInit()
    }

    private fun themeSettingInit(){
        val sharedPreferences = context?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val savedTheme = sharedPreferences?.getInt(KEY_THEME, THEME_SYSTEM_DEFAULT)
        when(savedTheme){
            THEME_SYSTEM_DEFAULT -> binding.systemTheme.isChecked = true
            THEME_LIGHT -> binding.light.isChecked = true
            THEME_DARK -> binding.dark.isChecked = true
        }
        binding.light.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) setTheme(THEME_LIGHT)
        }
        binding.dark.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) setTheme(THEME_DARK)
        }
        binding.systemTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) setTheme(THEME_SYSTEM_DEFAULT)
        }
    }

    private fun languageSettingInit(){
        val appLanguage = LanguageManager.getLocale(requireContext())
        when(appLanguage){
            Locale.ENGLISH -> binding.english.isChecked = true
            else -> binding.russian.isChecked = true
        }
        binding.russian.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                LanguageManager.setLocale(requireContext(), "ru")
                requireActivity().recreate()
            }
        }
        binding.english.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                LanguageManager.setLocale(requireContext(), "en")
                requireActivity().recreate()
            }
        }
    }

    private fun setTheme(mode: Int) {
        val sharedPreferences = context?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences?.edit()?.putInt(KEY_THEME, mode)?.apply()
        when(mode){
            THEME_LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            THEME_DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            THEME_SYSTEM_DEFAULT ->
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    private fun inAccountInit(){
        binding.AccountSetingsLayout.visibility = View.VISIBLE
        binding.exitAccountButton.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.exit_dialog_title)
                .setMessage(R.string.exit_dialog_text)
                .setPositiveButton(R.string.yes
                ) { _, _ -> exitAccount() }
                .setNegativeButton(R.string.no){dialog, _ ->
                    dialog.dismiss()
                }.create().show()
        }
        binding.username.text = Firebase.auth.currentUser?.email ?: "Error"
        binding.ResetPasswordButton.setOnClickListener {
            val email = Firebase.auth.currentUser?.email
            if (email != null){
                Firebase.auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context ,"Письмо для сброса пароля отправлено!",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            }

        }
    }

    private fun exitAccount(){
        Firebase.auth.signOut()
        Toast.makeText(context ,"Вы вышли из аккаунта!", Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()
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
package com.lnoxx.myanimeview

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object LanguageManager {

    fun setLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
        saveLanguagePreference(context, language)
    }

    fun getLocale(context: Context): Locale {
        val prefs = context.getSharedPreferences("animeViewPref", Context.MODE_PRIVATE)
        val language = prefs.getString("Language", "ru") // "en" is the default language
        return Locale(language!!)
    }

    private fun saveLanguagePreference(context: Context, language: String) {
        val prefs = context.getSharedPreferences("animeViewPref", Context.MODE_PRIVATE)
        prefs.edit().putString("Language", language).apply()
    }
}
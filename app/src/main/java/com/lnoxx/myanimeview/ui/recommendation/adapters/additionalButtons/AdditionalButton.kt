package com.lnoxx.myanimeview.ui.recommendation.adapters.additionalButtons

import com.lnoxx.myanimeview.R

enum class AdditionalButton(val titleResId: Int, val iconResId: Int) {
    RANDOM_ANIME(R.string.random_anime_button_title, R.drawable.outline_question_mark_24),
    SCHEDULE(R.string.schedules_button_title , R.drawable.round_calendar_today_24),
    CHARACTERS(R.string.charecters_button_title , R.drawable.round_people_alt_24),
    MAGAZINES(R.string.magazines_button_title , R.drawable.round_menu_book_24),
}
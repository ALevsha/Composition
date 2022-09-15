package com.procourse.composition.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class GameResult(
    val winner: Boolean,            // победили, или нет
    val countOfRightAnswers: Int,   // количество правильных ответов
    val countOfQuestions: Int,      // общее количество вопросов
    val gameSettings: GameSettings  // объект настроек игры
) : Parcelable

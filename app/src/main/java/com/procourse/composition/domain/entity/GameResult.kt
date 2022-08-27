package com.procourse.composition.domain.entity

import java.io.Serializable

data class GameResult(
    val winner: Boolean,// победили, или нет
    val countOfRightAnswers: Int,// количество правильных ответов
    val countOfQuestions: Int,// общее количество вопросов
    val gameSettings: GameSettings// объект настроек игры
) : Serializable

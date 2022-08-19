package com.procourse.composition.domain.entity

data class GameResult(
    val winner: Boolean,// победили, или нет
    val countOfRightAnswers: Int,// количество правильных ответов
    val countOfQuestions: Int,// общее количество вопросов
    val gameSettings: GameSettings// объект настроек игры
)
